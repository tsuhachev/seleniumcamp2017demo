package com.seleniumcamp.demo.currency;

import org.springframework.stereotype.Controller;

import java.util.Currency;

/**
 * This class is a implementation of CurrencyRateResource Rest API
 */
@Controller
public class CurrencyController implements CurrencyRateResourse {

    private static final String EUR = "EUR";
    private static final String USD = "USD";
    private static final double USD_TO_GBP = 0.81;
    private static final double USD_TO_EUR = 0.942065;

    @Override
    public RateDTO rate(String sourceCurrencyCode, String targetCurrencyCode) {
        double rate;
        Currency.getInstance(sourceCurrencyCode);
        Currency.getInstance(targetCurrencyCode);
        if (targetCurrencyCode.equals(sourceCurrencyCode)) {
            rate = 1;
        } else if (EUR.equals(targetCurrencyCode) && USD.equals(sourceCurrencyCode)) {
            rate = USD_TO_EUR;
        } else {
            rate = USD_TO_GBP;
        }
        return getRateDTO(rate);
    }

    private RateDTO getRateDTO(double rate) {
        RateDTO rateDTO = new RateDTO();
        rateDTO.setRate(rate);
        return rateDTO;
    }

}