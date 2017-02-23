package com.seleniumcamp.demo.wallet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Currency;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * This class is a unit test suite for WalletResourceController
 */
@RunWith(MockitoJUnitRunner.class)
public class WalletResourceControllerTest {

    public static final String EUR = "EUR";
    private WalletResourceController walletResourceController;

    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        walletResourceController = new WalletResourceController(restTemplate, "");
    }

    @Test
    public void testBalanceRateIsPositive() throws Exception {
        RateDTO rateDTO = new RateDTO();
        rateDTO.setRate(0.94);
        when(restTemplate
            .exchange(any(URI.class), eq(HttpMethod.GET), any(), eq(RateDTO.class)))
            .thenReturn(new ResponseEntity<>(rateDTO, HttpStatus.OK));

        BalanceDTO balanceDTO = walletResourceController.balance(EUR).getBody();

        assertEquals(Double.valueOf(94), balanceDTO.getBalance());
        assertEquals(Currency.getInstance(EUR), balanceDTO.getCurrency());
    }

    @Test
    public void testBalanceRateIsZero() throws Exception {
        RateDTO rateDTO = new RateDTO();
        rateDTO.setRate(0.0);
        when(restTemplate
            .exchange(any(URI.class), eq(HttpMethod.GET), any(), eq(RateDTO.class)))
            .thenReturn(new ResponseEntity<>(rateDTO, HttpStatus.OK));

        BalanceDTO balanceDTO = walletResourceController.balance(EUR).getBody();

        assertEquals(Double.valueOf(0), balanceDTO.getBalance());
        assertEquals(Currency.getInstance(EUR), balanceDTO.getCurrency());
    }

    @Test
    public void testCurrencies() throws Exception {
        assertThat(walletResourceController.currencies(), is(not(empty())));
    }
}