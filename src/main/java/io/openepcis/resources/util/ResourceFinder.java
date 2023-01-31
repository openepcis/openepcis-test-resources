package io.openepcis.resources.util;

import java.io.File;
import java.util.*;

/** Class that helps in finding the required resources based on the keyword. */
public class ResourceFinder {
  // Store all the files and based on provided keyword return the same
  private static final Map<String, List<File>> epcisFiles = new HashMap<>();
  private static final Deque<String> path = new ArrayDeque<>();

  static {
    getAllFiles("src/main/resources");
  }

  public static void main(String[] args) {
    searchEPCISDocument(EpcisVersion.VERSION_1_2, "xml", "ObjectEvent");
  }

  private static void getAllFiles(String directoryName) {
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
    String directoryPath = "";
    final Iterator<String> pathElements = path.descendingIterator();
    while (pathElements.hasNext()) {
      directoryPath =
          directoryPath.equals("")
              ? pathElements.next()
              : directoryPath + "/" + pathElements.next();
    }
    return directoryPath;
  }

  // Search based on provided version, format and keyword
  private static List<File> searchEPCISDocument(
      final EpcisVersion version, final String format, final String keyword) {
    List<File> filesList = new ArrayList<>();

    // If version is 1.2 then get files according to that
    if (EpcisVersion.VERSION_1_2.equals(version)) {
      for (final Map.Entry<String, List<File>> entry : epcisFiles.entrySet()) {
        if (entry.getKey().contains(version.getVersion())) {
          filesList.addAll(entry.getValue());
        }
      }
      System.out.println(filesList);
      return filesList;
    }
    return null;
  }
}
