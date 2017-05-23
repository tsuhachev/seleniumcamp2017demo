package com.seleniumcamp.demo.wallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

/**
 * This class is a implementation of WalletResource Rest API
 */
@Controller
public class WalletResourceController implements WalletResource {

    private static final String USER_CURRENCY = "USD";
    public static final Double CUSTOMER_BALANCE_USD = 100.0;
    private String currencyServiceRateEndpoint;

    private RestTemplate currencyRestTemplate;

    public WalletResourceController(@Autowired RestTemplate restTemplate,
        @Value("${currencyService.rateEndpoint}") String currencyServiceRateEndpoint) {
        this.currencyRestTemplate = restTemplate;
        this.currencyServiceRateEndpoint = currencyServiceRateEndpoint;
    }

    @Override
    public ResponseEntity<BalanceDTO> balance(String targetCurrencyCode) {
        UriComponentsBuilder builder = UriComponentsBuilder
            .fromUriString(currencyServiceRateEndpoint)
            .queryParam(RateDTO.SOURCE_CURRENCY_CODE, USER_CURRENCY)
            .queryParam(RateDTO.TARGET_CURRENCY_CODE, targetCurrencyCode);
        ResponseEntity<RateDTO> rateUsdToEur;
        try {
            rateUsdToEur = currencyRestTemplate
                .exchange(builder.build().encode().toUri(), HttpMethod.GET, null,
                    RateDTO.class);
        } catch (HttpServerErrorException e) {
            return new ResponseEntity<>(new BalanceDTO(), HttpStatus.SERVICE_UNAVAILABLE);
        }

        BalanceDTO balanceDTO = getBalanceDTO(targetCurrencyCode,
            CUSTOMER_BALANCE_USD * rateUsdToEur.getBody().getRate());

        return new ResponseEntity<>(balanceDTO, HttpStatus.OK);
    }

    @Override
    public List<Currency> currencies() {
        final ArrayList<Currency> currencies = new ArrayList<>();
        Stream.of(Locale.GERMANY, Locale.US, Locale.UK)
            .forEach(l -> currencies.add(Currency.getInstance(l)));
        return currencies;
    }

    private BalanceDTO getBalanceDTO(
        @RequestParam(RateDTO.TARGET_CURRENCY_CODE) String targetCurrencyCode,
        double balance) {
        BalanceDTO balanceDTO = new BalanceDTO();
        balanceDTO.setBalance(balance);
        balanceDTO.setCurrency(Currency.getInstance(targetCurrencyCode));
        return balanceDTO;
    }

}
