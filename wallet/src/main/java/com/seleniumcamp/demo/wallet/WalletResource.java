package com.seleniumcamp.demo.wallet;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Currency;
import java.util.List;

/**
 * This class is a WalletResource Rest API
 */
public interface WalletResource {

    String BALANCE_RESOURCE_PATH = "/balance";
    String CURRENCIES_RESOURCE_PATH = "/currencies";

    @RequestMapping(BALANCE_RESOURCE_PATH)
    @ResponseBody
    ResponseEntity<BalanceDTO> balance(
        @RequestParam(RateDTO.TARGET_CURRENCY_CODE) String targetCurrencyCode);

    @RequestMapping(path = CURRENCIES_RESOURCE_PATH, method = RequestMethod.GET)
    @ResponseBody
    List<Currency> currencies();
}