package com.example.demo.entity;

import com.google.gson.annotations.Expose;

import java.util.List;


public class ExchangeRates {
    public ExchangeRates() {
    }
    @Expose
    private String date;
    @Expose
    private String bank;
    @Expose
    private String baseCurrency;
    @Expose
    private String baseCurrencyLit;
    @Expose
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
