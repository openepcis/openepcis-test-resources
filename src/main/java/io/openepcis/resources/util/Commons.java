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

import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

import java.io.InputStream;

public class Commons {
  public static final String EPCIS_EPCISDOCUMENT_EPCISBODY_EVENT_LIST = "epcis:EPCISDocument.EPCISBody.EventList";

  // Read the provided file path as InputStream and return the values
  public static InputStream getInputStream(final String filePath) {
    return ResourceFinder.class.getClassLoader().getResourceAsStream(filePath);
  }

  // Get the XML path from provided response body
  public static XmlPath getXmlPath(final String responseBody) {
    // Create an XmlPath object from the response body
    return new XmlPath(responseBody);
  }

  // Get the XML path from provided response
  public static XmlPath getXmlPath(final Response response) {
    return getXmlPath(response.getBody().asString());
  }
}
