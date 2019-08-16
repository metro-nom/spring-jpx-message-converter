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

import org.junit.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static org.junit.Assert.*;

public class GpxMediaTypeTest {
    @Test
    public void shouldReturnAllMediaTypesForGpx() {
        List<MediaType> mediaTypes = GpxMediaType.mediaTypes();

        assertEquals(2, mediaTypes.size());

        assertTrue(mediaTypes.contains(MediaType.APPLICATION_XML));
        assertTrue(mediaTypes.contains(MediaType.valueOf("application/gpx+xml")));
    }
}