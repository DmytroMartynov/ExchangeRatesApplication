package com.example.demo.entity;

import com.google.gson.annotations.Expose;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicUpdate
public class Rate  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String date;
    @Expose
    private String currency;
    @Expose
    private String saleRate;
    @Expose
    private String purchaseRate;
    @Transient
    private String baseCurrency;
    @Transient
    private String saleRateNB;
    @Transient
    private String purchaseRateNB;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
