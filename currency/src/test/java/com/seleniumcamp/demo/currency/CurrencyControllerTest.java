package com.seleniumcamp.demo.currency;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * This class is a unit test for CurrencyController
 */
public class CurrencyControllerTest {

    public static final String USD = "USD";
    public static final String EUR = "EUR";
    private CurrencyController currencyController = new CurrencyController();

    @Test
    public void testRateUsdToEur() throws Exception {
        RateDTO rate = currencyController.rate(USD, EUR);
        assertNotNull(rate);
        assertEquals(Double.valueOf(0.942065), rate.getRate());
    }

    @Test
    public void testRateUsdToUsd() throws Exception {
        RateDTO rate = currencyController.rate(USD, USD);
        assertNotNull(rate);
        assertEquals(Double.valueOf(1), rate.getRate());
    }
}