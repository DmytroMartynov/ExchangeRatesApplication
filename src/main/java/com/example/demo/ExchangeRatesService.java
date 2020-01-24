package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.stream.Collectors;

import static com.example.demo.Constants.REQUEST_URL;

@Service
public class ExchangeRatesService {




    public String getRatesByDate(String date) throws IOException {
        String inline = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(REQUEST_URL + date);
        HttpResponse response = httpClient.execute(httpGet);
        int statusCode = response.getStatusLine().getStatusCode();
//        if (statusCode != 200) {
//            throw new RuntimeException("HttpStatus" + statusCode);
//        } else
//        {
//            Scanner sc = new Scanner(response.getEntity().getContent());
//            while (sc.hasNext()) {
//                inline += sc.nextLine();
//            }
//            System.out.println(inline);
//            sc.close();
//        }
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        String res = gson.toJson(inline);
//
//        System.out.println(res);

        HttpEntity entity = response.getEntity();
        ObjectMapper objectMapper = new ObjectMapper();

        ExchangeRates exchangeRates = objectMapper.readValue(entity.getContent(), ExchangeRates.class);
        String result = exchangeRates.getExchangeRate().stream()
                .filter(x -> "USD".equals(x.getCurrency())
                        || "EUR".equals(x.getCurrency())
                        || "RUB".equals(x.getCurrency()))
                .map(Rate::toString)
                .collect(Collectors.joining(","));
        return "[" + result + "]";
    }
}
