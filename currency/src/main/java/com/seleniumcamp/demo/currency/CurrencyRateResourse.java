package com.seleniumcamp.demo.currency;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * This class is a CurrencyRateResourse Rest API
 */
public interface CurrencyRateResourse {

    @RequestMapping(path = "/rate", method = RequestMethod.GET)
    @ResponseBody
    public RateDTO rate(
        @RequestParam(RateDTO.SOURCE_CURRENCY_CODE) String sourceCurrencyCode,
        @RequestParam(RateDTO.TARGET_CURRENCY_CODE) String targetCurrencyCode);
}
