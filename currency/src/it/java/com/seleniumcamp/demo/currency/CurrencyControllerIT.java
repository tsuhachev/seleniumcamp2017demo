package com.seleniumcamp.demo.currency;

import com.jayway.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * This class is an integration test for currency controller
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CurrencyControllerIT {

    private static final String USD = "USD";

    @Value("${local.server.port}")
    private int port;

    @Test
    public void testGetRateUSDtoUSD() throws Exception {
        Response response = given().port(port)
            .queryParam(RateDTO.SOURCE_CURRENCY_CODE, USD)
            .queryParam(RateDTO.TARGET_CURRENCY_CODE, USD)
            .get("/rate");

        assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        RateDTO rateDTO = response.as(RateDTO.class);
        assertNotNull(rateDTO);
        assertEquals(Double.valueOf(1), rateDTO.getRate());
    }
}