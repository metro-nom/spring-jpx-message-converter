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
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;

import static com.metronom.jpxMessageConverter.testUtil.GpxTestUtil.sampleGpx;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GpxMessageConverterTest {

    private GpxMessageConverter converter;

    @Before
    public void setUp() {
        converter = new GpxMessageConverter();
    }

    @Test
    public void shouldWriteGpx() throws Exception {
        GPX input = sampleGpx();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        HttpOutputMessage outputMessage = mock(HttpOutputMessage.class);
        when(outputMessage.getBody()).thenReturn(outputStream);

        converter.write(input, GpxMediaType.APPLICATION_GPX_XML.mediaType(), outputMessage);

        verify(outputMessage).getBody();
        String gpxString = outputStream.toString();
        GPX parsedOutputGpx = GPX.reader().fromString(gpxString);
        assertEquals(input, parsedOutputGpx);
    }

    @Test
    public void shouldReadGpx() throws Exception {
        GPX expected = sampleGpx();
        String gpxString = GPX.writer().toString(expected);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(gpxString.getBytes());
        HttpInputMessage inputMessage = mock(HttpInputMessage.class);

        when(inputMessage.getBody()).thenReturn(inputStream);

        GPX actual = converter.read(GPX.class, inputMessage);

        assertEquals(expected, actual);
    }

    @Test
    public void shouldBeAbleToReadGpxClassIfTheMediaTypeIsCorrect() {
        assertTrue(converter.canRead(GPX.class, null));
        assertTrue(converter.canRead(GPX.class, MediaType.APPLICATION_XML));
        assertTrue(converter.canRead(GPX.class, MediaType.valueOf("application/gpx+xml")));

        assertFalse(converter.canRead(GPX.class, MediaType.valueOf("application/json")));

        assertFalse(converter.canRead(Map.class, null));
        assertFalse(converter.canRead(Map.class, MediaType.APPLICATION_XML));
        assertFalse(converter.canRead(Map.class, MediaType.valueOf("application/gpx+xml")));
    }

    @Test
    public void shouldBeAbleToWriteGpxClassIfTheMediaTypeIsCorrect() {
        assertTrue(converter.canWrite(GPX.class, null));
        assertTrue(converter.canWrite(GPX.class, MediaType.APPLICATION_XML));
        assertTrue(converter.canWrite(GPX.class, MediaType.valueOf("application/gpx+xml")));

        assertFalse(converter.canWrite(GPX.class, MediaType.valueOf("application/json")));

        assertFalse(converter.canWrite(Map.class, null));
        assertFalse(converter.canWrite(Map.class, MediaType.APPLICATION_XML));
        assertFalse(converter.canWrite(Map.class, MediaType.valueOf("application/gpx+xml")));
    }
}
