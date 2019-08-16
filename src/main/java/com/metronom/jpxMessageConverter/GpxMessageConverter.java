/*    Copyright 2019 METRONOM GmbH
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.metronom.jpxMessageConverter;

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
                (mediaType == null || getSupportedMediaTypes().contains(mediaType));
    }
}
