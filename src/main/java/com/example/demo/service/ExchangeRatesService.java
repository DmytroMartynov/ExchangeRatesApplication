package com.example.demo.service;

import com.example.demo.entity.ExchangeRates;
import com.example.demo.entity.Rate;
import com.example.demo.repository.RateRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.demo.constants.Constants.REQUEST_URL;

@Service
public class ExchangeRatesService {

    private Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .create();

    private final RateRepository rateRepository;

    public ExchangeRatesService(RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    public String toJsonViewData(String date) {
        List<Rate> listRate = rateRepository.findByDate(date);
        String result = gson.toJson(listRate);
        System.out.println(result);
        return result;
    }

    public String getRatesByDateFromPrivatAPI(String date) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(REQUEST_URL + date);
        ExchangeRates exchangeRates = null;
        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            ObjectMapper objectMapper = new ObjectMapper();
            exchangeRates = objectMapper.readValue(entity.getContent(), ExchangeRates.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String result = Objects.requireNonNull(exchangeRates).getExchangeRate().stream()
                .filter(x -> "USD".equals(x.getCurrency())
                        || "EUR".equals(x.getCurrency())
                        || "RUB".equals(x.getCurrency()))
                .map(Rate::toString)
                .collect(Collectors.joining(","));
        return "[" + result + "]";
    }
}
