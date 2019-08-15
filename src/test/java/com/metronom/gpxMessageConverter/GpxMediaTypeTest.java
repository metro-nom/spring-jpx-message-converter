package com.metronom.gpxMessageConverter;

import org.junit.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static org.junit.Assert.*;

public class GpxMediaTypeTest {
    @Test
    public void shouldReturnAllMediaTypesForGpx() {
        List<MediaType> mediaTypes = GpxMediaType.mediaTypes();

        assertEquals(3, mediaTypes.size());

        assertTrue(mediaTypes.contains(null));
        assertTrue(mediaTypes.contains(MediaType.APPLICATION_XML));
        assertTrue(mediaTypes.contains(MediaType.valueOf("application/gpx+xml")));
    }
}