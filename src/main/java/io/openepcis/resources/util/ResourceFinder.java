/*
 * Copyright 2022-2024 benelog GmbH & Co. KG
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */
package io.openepcis.resources.util;

import io.openepcis.constants.EPCIS;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Class that helps in finding the required resources based on the version, format, keyword.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResourceFinder {
    // Store all the files and based on provided keyword return the same
    private static final List<String> EPCIS_TEST_RESOURCES = new ArrayList<>();
    private static final Deque<String> path = new ArrayDeque<>();

    static {
        loadFileList();
    }

    private static void loadFileList() {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(ResourceFinder.class.getResourceAsStream("/openepcis-test-resources.list")));
        EPCIS_TEST_RESOURCES.clear();
        try {
            String line = null;
            while ((line = reader.readLine()) != null) {
                EPCIS_TEST_RESOURCES.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method to search the resources list based on provided version, format, type, and keyword.
     *
     * @param version Document/event in required EPCIS version. Either 1.2 or 2.0
     * @param format  Document/event in required EPCIS format. Either XML/JSON.
     * @param type    Document/event type. Either Capture/Query.
     * @param keyword Document/event consisting of specific info ex: error, userExtension, sensorData,
     *                etc.
     */
    public static List<URL> searchResource(
            String version, String format, String type, String keyword) {
        final List<URL> matchingFiles = new ArrayList<>();
        version = !StringUtils.isBlank(version) ? version : EPCIS.SCHEMA_VERSION_2_0;
        keyword = !StringUtils.isBlank(keyword) ? keyword.toLowerCase() : "";
        format = !StringUtils.isBlank(format) ? format.toLowerCase() : "";
        type = !StringUtils.isBlank(type) ? type.toLowerCase() : EPCIS.CAPTURE.toLowerCase();

        for (final String file : EPCIS_TEST_RESOURCES) {
            if (!file.contains(version)) {
                continue;
            }

            boolean keywordMatched = StringUtils.isBlank(keyword);
            boolean formatMatched = StringUtils.isBlank(format);
            boolean typeMatched = StringUtils.isBlank(type);

            if (!keywordMatched && file.toLowerCase().contains(keyword)) {
                keywordMatched = true;
            }

            if (!formatMatched && file.toLowerCase().contains(format)) {
                formatMatched = true;
            }

            if (!typeMatched && file.toLowerCase().contains(type)) {
                typeMatched = true;
            }

            if (keywordMatched && formatMatched && typeMatched) {
                matchingFiles.add(ResourceFinder.class.getResource(file));
                keywordMatched = false;
                formatMatched = false;
            }
        }
        return matchingFiles;
    }


    public static URL matching(URL match, List<URL> from) {
        String file = match.getFile();
        file = file.substring(file.lastIndexOf("/"), file.lastIndexOf("."));
        final String f = file;
        return from.stream().filter(s -> {
            String m = s.getFile();
            m = m.substring(m.lastIndexOf("/"), m.lastIndexOf("."));
            return m.equals(f);
        }).findFirst().orElse(null);
    }

}
