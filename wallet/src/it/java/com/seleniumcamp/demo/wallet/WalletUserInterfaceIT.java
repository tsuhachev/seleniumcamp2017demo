package com.seleniumcamp.demo.wallet;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static com.codeborne.selenide.Selenide.$;
import static org.junit.Assert.assertEquals;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

/**
 * This class ...
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class WalletUserInterfaceIT {

    public static final double RATE_VALUE = 0.94;
    public static final String EUR = "EUR";
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8091);

    @Value("${local.server.port}")
    private int port;

    @Before
    public void setUp() throws Exception {
        WalletTestUtils.stubForRateEndpoint(HttpStatus.SC_OK, RATE_VALUE);
        Configuration.browser = "phantomjs";
        // do not forget to set phantomjs.binary.path
    }

    @Test
    public void testGetMyBalance() throws Exception {
        Selenide.open(getSiteUrl());
        $("#currenciesSelect").selectOptionContainingText(EUR);
        String myBalance = $("#myBalance").getText();
        assertEquals("94 EUR", myBalance);
    }

    private String getSiteUrl() {
        return fromHttpUrl("http://localhost")
            .port(port)
            .toUriString();
    }
}
