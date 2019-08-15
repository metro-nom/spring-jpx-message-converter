package com.metronom.jpxMessageConverter;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.jenetics.jpx.GPX;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static com.metronom.jpxMessageConverter.testUtil.GpxTestUtil.sampleGpx;
import static org.junit.Assert.assertEquals;

public class GpxMessageConverterIntegrationTest {

    @Rule
    public WireMockRule gpxServer = new WireMockRule(wireMockConfig().dynamicPort());

    @Test
    public void restTemplateShouldSendGPX() {
        String receiverPath = "/gpxReceiver";
        stubFor(
                post(receiverPath)
                        .willReturn(aResponse()
                                .withStatus(200)
                        )
        );

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(Collections.singletonList(new GpxMessageConverter()));

        URI url = URI.create(gpxServer.url(receiverPath));
        GPX gpx = sampleGpx();
        RequestEntity<GPX> entity = new RequestEntity<>(gpx, HttpMethod.POST, url);
        restTemplate.exchange(entity, String.class);

        verify(postRequestedFor(urlEqualTo(receiverPath))
                .withRequestBody(equalToXml(GPX.writer().toString(gpx)))
        );
    }

    @Test
    public void restTemplateShouldReceiveGPX() {
        String senderPath = "/gpxSender";

        GPX expected = sampleGpx();
        stubFor(
                get(senderPath)
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withBody(GPX.writer().toString(expected))
                                .withHeader("Content-Type", "application/gpx+xml")
                        )
        );

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(Collections.singletonList(new GpxMessageConverter()));

        URI url = URI.create(gpxServer.url(senderPath));

        RequestEntity<GPX> entity = new RequestEntity<>(HttpMethod.GET, url);
        GPX actual = restTemplate.exchange(entity, GPX.class).getBody();

        assertEquals(expected, actual);
    }
}
