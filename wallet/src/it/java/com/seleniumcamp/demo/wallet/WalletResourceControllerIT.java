package com.seleniumcamp.demo.wallet;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.jayway.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * This class is an integration test for WalletResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class WalletResourceControllerIT {

    public static final String USD = "USD";
    public static final Double RATE_VALUE = 0.81;
    public static final String EUR = "EUR";

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8091);

    @Value("${local.server.port}")
    private int port;

    @Test
    public void testGetBalanceCurrencyRateIsValid() throws Exception {
        WalletTestUtils.stubForRateEndpoint(HttpStatus.SC_OK, RATE_VALUE);

        Response response = given().port(port)
            .queryParam(RateDTO.TARGET_CURRENCY_CODE, EUR)
            .get(WalletResource.BALANCE_RESOURCE_PATH);

        verify(getRequestedFor(urlPathEqualTo(WalletTestUtils.RATE_PATH))
            .withQueryParam(RateDTO.SOURCE_CURRENCY_CODE, equalTo(USD))
            .withQueryParam(RateDTO.TARGET_CURRENCY_CODE, equalTo(EUR)));

        assertEquals(HttpStatus.SC_OK, response.getStatusCode());

        BalanceDTO balanceDTO = response.as(BalanceDTO.class);

        assertEquals(
            Double.valueOf(WalletResourceController.CUSTOMER_BALANCE_USD * RATE_VALUE),
            balanceDTO.getBalance());
        assertEquals(EUR, balanceDTO.getCurrency().getCurrencyCode());

    }

    @Test
    public void testGetBalanceRateEndpointThrowsError() throws Exception {
        WalletTestUtils.stubForRateEndpoint(HttpStatus.SC_INTERNAL_SERVER_ERROR,
            RATE_VALUE);

        Response response = given().port(port)
            .queryParam(RateDTO.TARGET_CURRENCY_CODE, EUR)
            .get(WalletResource.BALANCE_RESOURCE_PATH);

        assertEquals(HttpStatus.SC_SERVICE_UNAVAILABLE, response.getStatusCode());
    }

    @Test
    public void testGetCurrencies() throws Exception {
        String[] currencies = given().port(port)
            .when().get(WalletResource.CURRENCIES_RESOURCE_PATH)
            .then().statusCode(HttpStatus.SC_OK)
            .extract().response().as(String[].class);

        assertThat(Arrays.asList(currencies), is(not(empty())));
    }

}