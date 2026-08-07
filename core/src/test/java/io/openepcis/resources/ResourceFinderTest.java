/*
 * Copyright 2022-2026 benelog GmbH & Co. KG
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
package io.openepcis.resources;

import io.openepcis.resources.util.ResourceFinder;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ResourceFinderTest {

    @Test
    void findResourceWith12VersionAndFormatTest() {
        final List<URL> filesList = ResourceFinder.searchResource("1.2", "xml", "capture", "ObjectEvent");
        assertFalse(filesList.isEmpty());
    }

    @Test
    void findResource12WithVersionTest() {
        final List<URL> filesList = ResourceFinder.searchResource("1.2", "xml", "capture", null);
        assertFalse(filesList.isEmpty());
    }

    @Test
    void findResource20WithVersionTest() {
        final List<URL> filesList = ResourceFinder.searchResource("1.2", "json", "capture", null);
        assertEquals(0, filesList.size());
    }

    @Test
    void findResource20WithVersionKeywordTest() {
        final List<URL> filesList = ResourceFinder.searchResource("2.0", "json", "capture", "error");
        assertFalse(filesList.isEmpty());
    }

    @Test
    void findResource20WithoutKeywordTest() {
        final List<URL> filesList = ResourceFinder.searchResource("2.0", "json", "capture", null);
        assertFalse(filesList.isEmpty());
    }

    @Test
    void findResource20QueryDocumentTest() {
        final List<URL> filesList = ResourceFinder.searchResource("2.0", "json", "query", null);
        assertFalse(filesList.isEmpty());
    }
}
