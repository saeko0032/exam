package com.example.saeko.currencyapp.network.model;

/**
 * Created by saeko on 2018-01-07.
 */

public class CurrencyInfo {

    private String name;
    private double value;

    public CurrencyInfo(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
