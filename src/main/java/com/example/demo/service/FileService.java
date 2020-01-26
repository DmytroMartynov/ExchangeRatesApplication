package com.example.demo.service;

import com.example.demo.entity.Rate;
import com.example.demo.repository.RateRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.example.demo.constants.Constants.UPLOADED_FOLDER;

@Service
public class FileService {
    private final RateRepository rateRepository;

    public FileService(RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    private List<String> unZipIt(String inputFile, Path outputFolder, MultipartFile file) {
        Stream<Path> subPath = null;
        try {
            byte[] buffer = file.getBytes();
            File folder = new File(outputFolder.toString());
            if (!folder.exists()) {
                folder.mkdir();
            }
            ZipInputStream zis = new ZipInputStream(new FileInputStream(inputFile));
            ZipEntry ze = zis.getNextEntry();
            while (ze != null) {
                String fileName = ze.getName();
                File newFile = new File(outputFolder + File.separator + fileName);
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                ze = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
            subPath = Files.walk(outputFolder, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> res = subPath
                .filter(Files::isRegularFile)
                .map(Objects::toString)
                .collect(Collectors.toList());
        return res;
    }

    private void saveFile(Path filePath, MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            Files.write(filePath, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String downloadFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
        }
        Path filePath = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
        String filename = file.getOriginalFilename();
        if (filename != null) {
            String fileDate = filename.substring(0, filename.lastIndexOf('.'));
            Path unZipFolder = Paths.get(UPLOADED_FOLDER + fileDate);
            saveFile(filePath, file);
            if (filename.endsWith(".zip")) {
                List<String> unZipFiles = unZipIt(filePath.toString(), unZipFolder, file);
                for (int i = 0; i < unZipFiles.size(); i++) {
                    Path temp = Paths.get(unZipFiles.get(i));
                    String s = temp.getFileName().toString();
                    saveRateDataToDB(temp, s.substring(0, s.lastIndexOf('.')));
                }
            } else if (filename.endsWith(".csv")) {
                saveRateDataToDB(filePath, fileDate);
            }
        }
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded '" + file.getOriginalFilename() + "'");
        return "redirect:/uploadStatus";
    }

    public void saveRateDataToDB(Path filePath, String fileDate) {
        if (filePath != null) {
            List<Rate> rates = rateRepository.findAll();
            boolean contains = rates.stream().anyMatch(x -> x.getDate().equals(fileDate));
            int i = 3;
            try {
                BufferedReader br = new BufferedReader(new FileReader(filePath.toString()));
                String line = "";
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    if (!contains) {
                        Rate rate = new Rate();
                        setRateFields(rate, data, fileDate);
                        rateRepository.save(rate);
                    } else {
                        int getLastElement = rates.get(rates.size() - i--).getId();
                        Rate rate2 = rateRepository.findOneById(getLastElement);
                        setRateFields(rate2, data, fileDate);
                        rateRepository.save(rate2);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setRateFields(Rate rate, String[] data, String fileDate) {
        rate.setCurrency(data[0]);
        rate.setSaleRate(data[1]);
        rate.setPurchaseRate(data[2]);
        rate.setDate(fileDate);
    }
}
