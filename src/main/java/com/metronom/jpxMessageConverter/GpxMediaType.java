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
