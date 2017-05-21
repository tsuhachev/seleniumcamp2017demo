package com.seleniumcamp.demo.wallet;

import com.github.tomakehurst.wiremock.client.WireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;

/**
 * This class wallet test helper
 */
class WalletTestUtils {

    static final String RATE_PATH = "/rate";

    static void stubForRateEndpoint(int responseStatusCode, Double rateValue) {
        stubFor(
            get(urlPathMatching(RATE_PATH))
                .withQueryParam(RateDTO.SOURCE_CURRENCY_CODE, WireMock.matching(".*"))
                .withQueryParam(RateDTO.TARGET_CURRENCY_CODE, WireMock.matching(".*"))
                .willReturn(aResponse()
                    .withHeader("Content-type", "application/json")
                    .withStatus(responseStatusCode)
                    .withBody(
                        "{\"rate\":" + rateValue + "}")));
    }
}
