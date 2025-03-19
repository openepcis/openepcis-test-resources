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
package io.openepcis.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import io.openepcis.resources.util.ResourceFinder;
import java.net.URL;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ResourceFinderTest {

  @Before
  public void before() {}

  @Test
  public void findResourceWith12VersionAndFormatTest() {
    final List<URL> filesList =
        ResourceFinder.searchResource("1.2", "xml", "capture", "ObjectEvent");
    assertTrue(filesList.size() > 0);
  }

  @Test
  public void findResource12WithVersionTest() {
    final List<URL> filesList = ResourceFinder.searchResource("1.2", "xml", "capture", null);
    assertTrue(filesList.size() > 0);
  }

  @Test
  public void findResource20WithVersionTest() {
    final List<URL> filesList = ResourceFinder.searchResource("1.2", "json", "capture", null);
    assertEquals(filesList.size(), 0);
  }

  @Test
  public void findResource20WithVersionKeywordTest() {
    final List<URL> filesList = ResourceFinder.searchResource("2.0", "json", "capture", "error");
    assertTrue(filesList.size() > 0);
  }

  @Test
  public void findResource20WithoutKeywordTest() {
    final List<URL> filesList = ResourceFinder.searchResource("2.0", "json", "capture", null);
    assertTrue(filesList.size() > 0);
  }

  @Test
  public void findResource20QueryDocumentTest() {
    final List<URL> filesList = ResourceFinder.searchResource("2.0", "json", "query", null);
    assertTrue(filesList.size() > 0);
  }
}
