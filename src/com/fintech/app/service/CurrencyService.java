package com.fintech.app.service;

public class CurrencyService {

    public double convert(String sourceCurrency, String targetCurrency, double amount) {
        if (sourceCurrency.equalsIgnoreCase(targetCurrency)) {
            return amount;
        }

        // Dummy conversion rates for now
        if (sourceCurrency.equalsIgnoreCase("INR") && targetCurrency.equalsIgnoreCase("USD")) {
            return amount / 93.0;
        }

        if (sourceCurrency.equalsIgnoreCase("USD") && targetCurrency.equalsIgnoreCase("INR")) {
            return amount * 93.0;
        }

        return amount;
    }
}