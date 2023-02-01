package io.openepcis.resources.util;

import java.io.File;
import java.util.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/** Class that helps in finding the required resources based on the keyword. */
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
    if (fList != null) {
      for (File file : fList) {
        if (file.isFile() && !file.getName().equals(".DS_Store")) {
          List<File> existingFiles = epcisFiles.get(getDirectoryPath());
          if (existingFiles == null) {
            existingFiles = new ArrayList<>();
          }
          existingFiles.add(file);
          epcisFiles.put(getDirectoryPath(), existingFiles);
        } else if (file.isDirectory()) {
          path.push(file.getName());
          getAllFiles(file.getAbsolutePath());
          path.pop();
        }
      }
    }
  }

  // Method to concatenate the path in Deque and return the concatenated String
  private static String getDirectoryPath() {
    StringBuilder directoryPath = new StringBuilder();
    final Iterator<String> pathElements = path.descendingIterator();
    while (pathElements.hasNext()) {
      directoryPath.insert(0, pathElements.next());
      if (pathElements.hasNext()) {
        directoryPath.insert(0, File.separator);
      }
    }
    return directoryPath.toString();
  }

  // Search based on provided version, format and keyword
  public static List<File> searchEPCISDocument(String version, String format, String keyword) {
    final List<File> matchingFiles = new ArrayList<>();
    version = !StringUtils.isBlank(version) ? version : EpcisVersion.VERSION_2_0.getVersion();
    keyword = !StringUtils.isBlank(keyword) ? keyword.toLowerCase() : "";
    format = !StringUtils.isBlank(format) ? format.toLowerCase() : "";

    for (final Map.Entry<String, List<File>> entry : epcisFiles.entrySet()) {
      if (!entry.getKey().contains(version)) {
        continue;
      }

      final List<File> files = entry.getValue();
      boolean keywordMatched = StringUtils.isBlank(keyword);
      boolean formatMatched = StringUtils.isBlank(format);

      for (File file : files) {
        if (!keywordMatched && file.getName().toLowerCase().contains(keyword)) {
          keywordMatched = true;
        }
        if (!formatMatched && entry.getKey().toLowerCase().contains(format)) {
          formatMatched = true;
        }
        if (keywordMatched && formatMatched) {
          matchingFiles.add(file);
          keywordMatched = false;
          formatMatched = false;
        }
      }
    }
    return matchingFiles;
  }
}
