package com.example.demo;

import java.util.List;


public class ExchangeRates {

    private String date;
    private String bank;
    private String baseCurrency;
    private String baseCurrencyLit;
    private List< Rate > exchangeRate;

    public String getBank() {
        return bank;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public String getBaseCurrencyLit() {
        return baseCurrencyLit;
    }

    public String getDate() {
        return date;
    }


    public List<Rate> getExchangeRate() {
        return exchangeRate;
    }

}
