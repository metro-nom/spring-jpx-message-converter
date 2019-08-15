package com.metronom.gpxMessageConverter;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.jenetics.jpx.GPX;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
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
        GPX gpx = buildSampleGpx();
        RequestEntity<GPX> entity = new RequestEntity<>(gpx, HttpMethod.POST, url);
        restTemplate.exchange(entity, String.class);

        verify(postRequestedFor(urlEqualTo(receiverPath))
                .withRequestBody(equalToXml(GPX.writer().toString(gpx)))
        );
    }

    @Test
    public void restTemplateShouldReceiveGPX() {
        String senderPath = "/gpxSender";

        GPX expected = buildSampleGpx();
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

    private GPX buildSampleGpx() {
        long firstTimestamp = LocalDateTime.of(2019, 8, 15, 10, 50, 0).toEpochSecond(ZoneOffset.UTC) * 1000;
        long secondTimestamp = firstTimestamp + 1000;

        return GPX.builder()
                .addTrack(track -> track.addSegment(
                        segment -> segment
                                .addPoint(point -> point.lat(49.0).lon(8.0).time(firstTimestamp))
                                .addPoint(point -> point.lat(49.5).lon(8.5).time(secondTimestamp))
                )).build();
    }
}
