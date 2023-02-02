[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

<div align="justify">

# openepcis-test-resources
The project encompasses an extensive collection of EPCIS events in both XML and JSON/JSON-LD formats, meticulously crafted to fulfill diverse testing requirements.

## Introduction

The development of applications that comply with the EPCIS standard requires rigorous testing using a wide spectrum of EPCIS documents and events. This task can be particularly challenging due to 
the difficulty of maintaining and organizing these EPCIS documents and events, especially given their frequent usage across multiple projects. In an effort to streamline this process, OpenEPCIS 
developed this project that centralizes the management of different types of EPCIS documents and events in XML and JSON/JSON-LD formats. With this application, users can effortlessly 
access the required EPCIS documents and events, saving valuable time and reducing repetitive manual effort.

## Usage
Users can easily locate the desired resources by utilizing the `searchResource` method within the `ResourceFinder` class. Simply by providing the required EPCIS version, EPCIS format, and keyword as parameters, the associated resource will be searched, and a list of matching files will be returned to the user for their convenience.

As an example, if a user requires an EPCIS 2.0 document/event in JSON/JSON-LD format with an `errorDeclaration`, they can employ the following approach:

```java
final List<File> filesList = ResourceFinder.searchResource("2.0", "json", "error");
```

In a similar manner, if a user requires EPCIS 1.2 documents without any additional parameters, they can use the following approach:
```java
final List<File> filesList = ResourceFinder.searchResource("1.2", "", null);
```

Following code returns list of all EPCIS document/events in JSON format for EPCIS 2.0 version
```java
final List<File> filesList = ResourceFinder.searchResource("2.0", "json", "");
```
</div>