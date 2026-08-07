<p align="center">
  <img src="https://openepcis.io/img/openepcis-logo.svg" alt="OpenEPCIS" width="30%">
</p>

[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](LICENSE)
[![Release](https://img.shields.io/github/v/release/openepcis/openepcis-test-resources)](https://github.com/openepcis/openepcis-test-resources/releases)
[![Stars](https://img.shields.io/github/stars/openepcis/openepcis-test-resources?style=social)](https://github.com/openepcis/openepcis-test-resources)

<h1 align="center">OpenEPCIS Test Resources</h1>

Collection of [GS1 EPCIS](https://www.gs1.org/standards/epcis) documents and events in XML and JSON/JSON-LD. Includes both valid and invalid documents/events. Can be used in any
project for testing purposes and cover wide range of scenarios.

## Why

Building applications that comply with the EPCIS standard requires testing against a wide range of
documents and events. Maintaining and organising these documents can be challenging, especially if the same
documents are needed across several projects. This project centralises them, so any OpenEPCIS or other projects
can use the documents/events from here.

## Installation

```xml

<dependency>
    <groupId>io.openepcis</groupId>
    <artifactId>openepcis-test-resources</artifactId>
    <version>${openepcis-test-resources.version}</version>
    <scope>test</scope>
</dependency>
```

## Usage

### Load one document

Use `Commons.getInputStream` with the path of the document you want.

```java
final InputStream document = Commons.getInputStream("2.0/EPCIS/JSON/Capture/Documents/ObjectEvent.json");
```

### Search for documents

Use `ResourceFinder.searchResource` with an EPCIS version, a format, a type and a keyword. Any
argument can be left blank to widen the search.

```java
// EPCIS 2.0 capture documents in JSON that contain an errorDeclaration
final List<URL> withError = ResourceFinder.searchResource("2.0", "json", "capture", "error");

// every EPCIS 1.2 document
final List<URL> all12 = ResourceFinder.searchResource("1.2", "", "", null);

// every EPCIS 2.0 document in JSON
final List<URL> all20Json = ResourceFinder.searchResource("2.0", "json", "", "");
```

`ResourceFinder.matching` pairs an XML document with its JSON counterpart by file name, which is
handy for round-trip and hash comparison tests. Keep the two names identical when adding a pair.

## Contents

Documents are organised by EPCIS version, then format, then Capture or Query.

```
core/src/main/resources/
├── 1.2/EPCIS/XML/Capture/Documents/     19 documents
└── 2.0/EPCIS/
    ├── JSON/
    │   ├── Capture/Documents/           38 documents
    │   ├── Capture/Events/               5 bare events
    │   ├── Capture/Invalid/             15 invalid documents
    │   ├── Query/                       11 documents
    │   └── Query/Invalid/                1 invalid document
    └── XML/
        ├── Capture/Documents/           37 documents
        ├── Capture/Events/               6 bare events
        ├── Capture/Invalid/              6 invalid documents
        ├── Query/                        9 documents
        ├── Query/Documents/              2 documents
        └── Query/Invalid/                1 invalid document
```

## Invalid documents

Along with the valid documents, this project includes documents that intentionally violate the EPCIS
standard. Use them for negative testing: checking that a service rejects invalid input, and that the
error it reports is the right one. They are present in `Invalid` folder next to the valid
documents, split the same way by version, format and Capture or Query.

```java
final InputStream document = Commons.getInputStream("2.0/EPCIS/JSON/Capture/Invalid/ObjectEvent_with_non_standard_bizStep.json");
```

> [!NOTE]
> `ResourceFinder.searchResource` intentionally does **not** return invalid documents. When a users request
> for documents by version, format and keyword, the assumption is that the results are usable.
> Returning a broken document there would be confusing, so the `Invalid` folder is left out of
> `openepcis-test-resources.list`.

## Generate the resource file list

`ResourceFinder` reads `openepcis-test-resources.list` to discover what is available. Regenerate it
after adding or renaming any document.

```shell
mvn clean verify -Pgenerate-resource-file-list
```

## Contributing

We welcome all sorts of contributions such as bug reports, new documents, and corrections to existing documents, etc.
When adding a document, please follow the folder layout described above, use a name that describes the content, and
regenerate the resource file list.

## Related

- [OpenEPCIS Tools](https://tools.openepcis.io/) - open source EPCIS 2.0 tools and services
- [OpenEPCIS](https://openepcis.io/) - Read more about OpenEPCIS
- [benelog GmbH & Co. KG](https://www.benelog.com/) - Company behind the OpenEPCIS
- [GS1 EPCIS Standard](https://www.gs1.org/standards/epcis) - Learn more about EPCIS

## License

Licensed under the [Apache License 2.0](LICENSE).
