package com.metronom.gpxMessageConverter.testUtil;

import io.jenetics.jpx.GPX;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class GpxTestUtil {
    private GpxTestUtil() {}

    public static GPX sampleGpx() {
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
