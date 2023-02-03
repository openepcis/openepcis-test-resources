package io.openepcis.resources.util;

import io.openepcis.constants.EPCIS;
import java.io.File;
import java.util.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/** Class that helps in finding the required resources based on the version, format, keyword. */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResourceFinder {
  // Store all the files and based on provided keyword return the same
  private static final Map<String, List<File>> epcisFiles = new HashMap<>();
  private static final Deque<String> path = new ArrayDeque<>();

  static {
    getAllFiles("src/main/resources");
  }

  private static void getAllFiles(final String directoryName) {
    final File directory = new File(directoryName);
    final File[] fList = directory.listFiles();

    // Accept only the filesList without null
    if (fList != null) {
      // Loop through the filesList and store
      for (File file : fList) {
        // Ignore if not file and hidden files
        if (file.isFile() && !file.isHidden()) {
          // Get the list of files based on the directory
          List<File> existingFiles = epcisFiles.get(getDirectoryPath());

          if (existingFiles == null) {
            existingFiles = new ArrayList<>();
          }

          // Store the files to Map
          existingFiles.add(file);
          epcisFiles.put(getDirectoryPath(), existingFiles);
        } else if (file.isDirectory()) {
          // If directory then recurse and find files
          path.push(file.getName());
          getAllFiles(file.getAbsolutePath());
          path.pop();
        }
      }
    }
  }

  // Method to concatenate the path in Deque and return the concatenated String
  private static String getDirectoryPath() {
    final StringBuilder directoryPath = new StringBuilder();
    final Iterator<String> pathElements = path.descendingIterator();
    while (pathElements.hasNext()) {
      directoryPath.insert(0, pathElements.next());
      if (pathElements.hasNext()) {
        directoryPath.insert(0, File.separator);
      }
    }
    return directoryPath.toString();
  }

  /**
   * Method to search the resources list based on provided version, format, type, and keyword.
   *
   * @param version Document/event in required EPCIS version. Either 1.2 or 2.0
   * @param format Document/event in required EPCIS format. Either XML/JSON.
   * @param type Document/event type. Either Capture/Query.
   * @param keyword Document/event consisting of specific info ex: error, userExtension, sensorData,
   *     etc.
   */
  public static List<File> searchResource(
      String version, String format, String type, String keyword) {
    final List<File> matchingFiles = new ArrayList<>();
    version = !StringUtils.isBlank(version) ? version : EPCIS.SCHEMA_VERSION_2_0;
    keyword = !StringUtils.isBlank(keyword) ? keyword.toLowerCase() : "";
    format = !StringUtils.isBlank(format) ? format.toLowerCase() : "";
    type = !StringUtils.isBlank(type) ? type.toLowerCase() : EPCIS.CAPTURE.toLowerCase();

    for (final Map.Entry<String, List<File>> entry : epcisFiles.entrySet()) {
      if (!entry.getKey().contains(version)) {
        continue;
      }

      final List<File> files = entry.getValue();
      boolean keywordMatched = StringUtils.isBlank(keyword);
      boolean formatMatched = StringUtils.isBlank(format);
      boolean typeMatched = StringUtils.isBlank(type);

      for (final File file : files) {
        if (!keywordMatched && file.getName().toLowerCase().contains(keyword)) {
          keywordMatched = true;
        }

        if (!formatMatched && entry.getKey().toLowerCase().contains(format)) {
          formatMatched = true;
        }

        if (!typeMatched && entry.getKey().toLowerCase().contains(type)) {
          typeMatched = true;
        }

        if (keywordMatched && formatMatched && typeMatched) {
          matchingFiles.add(file);
          keywordMatched = false;
          formatMatched = false;
        }
      }
    }
    return matchingFiles;
  }
}
