package io.openepcis.resources.oas;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.eclipse.microprofile.openapi.OASFactory;
import org.eclipse.microprofile.openapi.OASFilter;
import org.eclipse.microprofile.openapi.models.Components;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.eclipse.microprofile.openapi.models.examples.Example;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@RegisterForReflection
public class EPCISExampleOASFilter implements OASFilter {

  public static final String EXAMPLE_2_0_0_XML_DOCUMENT = "xmlDocument";
  public static final String EXAMPLE_1_2_0_XML_DOCUMENT = "xml1.2Document";
  public static final String EXAMPLE_2_0_0_JSON_DOCUMENT = "jsonDocument";
  public static final String EXAMPLE_2_0_0_JSON_EVENT_LIST = "jsonEventsList";

  public static final String EXAMPLE_2_0_0_XML_OBJECT_EVENT = "xmlObjectEvent";
  protected final ObjectMapper objectMapper = new ObjectMapper();

  protected Components defaultComponents;
  @Override
  public void filterOpenAPI(OpenAPI openAPI) {
    try {
      defaultComponents = OASFactory.createComponents();
      if (openAPI.getComponents() == null) {
        openAPI.setComponents(defaultComponents);
      }

      generateExamples().forEach(openAPI.getComponents()::addExample);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  Map<String, Example> generateExamples() throws IOException {
    final Map<String, Example> examples = new LinkedHashMap<>();

    // EPCIS Events in XML format
    final String epcisXmlEvents =
        new String(
            Objects.requireNonNull(
                    getClass()
                        .getClassLoader()
                        .getResourceAsStream(
                            "2.0/EPCIS/XML/Capture/Documents/Combination_of_different_event.xml"))
                .readAllBytes());

    final String epcisXmlEvents2 =
        new String(
            Objects.requireNonNull(
                    getClass()
                        .getClassLoader()
                        .getResourceAsStream(
                            "1.2/EPCIS/XML/Capture/Documents/All_eventTypes_in_single_document.xml"))
                .readAllBytes());

    final String epcisXmlObjectEvent =
            new String(
                    Objects.requireNonNull(
                                    getClass()
                                            .getClassLoader()
                                            .getResourceAsStream(
                                                    "2.0/EPCIS/XML/Capture/Events/ObjectEvent.xml"))
                            .readAllBytes());

    // EPCIS events in JSON/JSON-LD format
    final String epcisJsonEvents =
        new String(
            Objects.requireNonNull(
                    getClass()
                        .getClassLoader()
                        .getResourceAsStream(
                            "2.0/EPCIS/JSON/Capture/Documents/ObjectEvent_with_sensorData.json"))
                .readAllBytes());

    // EPCIS events list in JSON/JSON-LD format
    final String epcisJsonEventsList =
            "[\n".concat(
        new String(
            Objects.requireNonNull(
                    getClass()
                        .getClassLoader()
                        .getResourceAsStream(
                            "2.0/EPCIS/JSON/Capture/Events/ObjectEvent.json"))
                .readAllBytes()))
              .concat(",\n").concat(
                  new String(
                          Objects.requireNonNull(
                                          getClass()
                                                  .getClassLoader()
                                                  .getResourceAsStream(
                                                          "2.0/EPCIS/JSON/Capture/Events/TransformationEvent.json"))
                                  .readAllBytes()))
                    .concat("\n]");

    // Adding EPCIS XML files
    examples.put(EXAMPLE_2_0_0_XML_DOCUMENT, OASFactory.createExample().value(epcisXmlEvents));
    examples.put(EXAMPLE_1_2_0_XML_DOCUMENT, OASFactory.createExample().value(epcisXmlEvents2));

    // Adding EPCIS JSON/JSON-LD files
    examples.put(
            EXAMPLE_2_0_0_JSON_DOCUMENT,
        OASFactory.createExample()
            .value(objectMapper.readValue(epcisJsonEvents, ObjectNode.class)));

    examples.put(EXAMPLE_2_0_0_JSON_EVENT_LIST, OASFactory.createExample().value(epcisJsonEventsList));
    examples.put(EXAMPLE_2_0_0_XML_OBJECT_EVENT, OASFactory.createExample().value(epcisXmlObjectEvent));
    return examples;
  }
}
