package com.metronom.gpxMessageConverter;

import io.jenetics.jpx.GPX;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.util.List;

public class GpxMessageConverter implements HttpMessageConverter<GPX> {
    @Override
    public boolean canRead(Class<?> clazz, @Nullable MediaType mediaType) {
        return canReadOrWrite(clazz, mediaType);
    }

    @Override
    public boolean canWrite(Class<?> clazz, @Nullable MediaType mediaType) {
        return canReadOrWrite(clazz, mediaType);
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return GpxMediaType.mediaTypes();
    }

    @Override
    public GPX read(Class<? extends GPX> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return GPX.read(inputMessage.getBody());
    }

    @Override
    public void write(GPX gpx, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        GPX.writer().write(gpx, outputMessage.getBody());
    }

    private boolean canReadOrWrite(Class<?> clazz, @Nullable MediaType mediaType) {
        return clazz == GPX.class &&
                getSupportedMediaTypes().contains(mediaType);
    }
}
