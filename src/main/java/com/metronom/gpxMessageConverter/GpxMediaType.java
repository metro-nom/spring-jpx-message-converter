package com.metronom.gpxMessageConverter;

import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum GpxMediaType {
    APPLICATION_GPX_XML("application/gpx+xml"),
    APPLICATION_XML(MediaType.APPLICATION_XML);

    private final MediaType _mediaType;


    GpxMediaType(MediaType mediaType) {
        this._mediaType = mediaType;
    }

    GpxMediaType(String mediaType) {
        this._mediaType = MediaType.valueOf(mediaType);
    }

    public MediaType mediaType() {
        return _mediaType;
    }

    public static List<MediaType> mediaTypes() {
        return Arrays.stream(values())
                .map(GpxMediaType::mediaType)
                .collect(Collectors.toList());
    }
}
