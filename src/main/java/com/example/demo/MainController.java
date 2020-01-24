package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class MainController {

   private final ExchangeRatesService exchangeRatesService;

    public MainController(ExchangeRatesService exchangeRatesService) {
        this.exchangeRatesService = exchangeRatesService;
    }
    /**
     * @param date - Pattern(dd.mm.yyyy)
     */
    @GetMapping("/date/{date}")
    public ResponseEntity getExchangeRates(@PathVariable("date") String date) throws IOException {
        return ResponseEntity.ok(exchangeRatesService.getRatesByDate(date));
    }
}
