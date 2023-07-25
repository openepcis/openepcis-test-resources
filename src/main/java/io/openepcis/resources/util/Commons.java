package io.openepcis.resources.util;

import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

import java.io.InputStream;

public class Commons {
  public static final String XML_PATH = "epcis:EPCISDocument.EPCISBody.EventList";

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
