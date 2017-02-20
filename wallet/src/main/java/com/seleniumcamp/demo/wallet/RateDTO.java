package com.seleniumcamp.demo.wallet;

/**
 * This class rate dto
 */
public class RateDTO {

    public static final String TARGET_CURRENCY_CODE = "targetCurrencyCode";
    public static final String SOURCE_CURRENCY_CODE = "sourceCurrencyCode";
    private Double rate;

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
