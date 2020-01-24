package com.example.demo;

public class Rate  {
    private String baseCurrency;
    private String currency;
    private String saleRateNB;
    private String purchaseRateNB;
    private String saleRate;
    private String purchaseRate;

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public String getSaleRateNB() {
        return saleRateNB;
    }

    public String getPurchaseRateNB() {
        return purchaseRateNB;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSaleRate() {
        return saleRate;
    }

    public void setSaleRate(String saleRate) {
        this.saleRate = saleRate;
    }

    public String getPurchaseRate() {
        return purchaseRate;
    }

    public void setPurchaseRate(String purchaseRate) {
        this.purchaseRate = purchaseRate;
    }

    @Override
    public String toString() {
        return "{" +
                "\"currency\":\"" + currency + '\"' +
                ",\"saleRate\":" + saleRate +
                ",\"purchaseRate\":" + purchaseRate +
                "}";
    }
}
