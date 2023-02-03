package io.openepcis.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import io.openepcis.resources.util.ResourceFinder;
import java.io.File;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ResourceFinderTest {

  @Before
  public void before() {}

  @Test
  public void findResourceWith12VersionAndFormatTest() {
    final List<File> filesList =
        ResourceFinder.searchResource("1.2", "xml", "capture", "ObjectEvent");
    assertTrue(filesList.size() > 0);
  }

  @Test
  public void findResource12WithVersionTest() {
    final List<File> filesList = ResourceFinder.searchResource("1.2", "xml", "capture", null);
    assertTrue(filesList.size() > 0);
  }

  @Test
  public void findResource20WithVersionTest() {
    final List<File> filesList = ResourceFinder.searchResource("1.2", "json", "capture", null);
    assertEquals(filesList.size(), 0);
  }

  @Test
  public void findResource20WithVersionKeywordTest() {
    final List<File> filesList = ResourceFinder.searchResource("2.0", "json", "capture", "error");
    assertTrue(filesList.size() > 0);
  }

  @Test
  public void findResource20WithoutKeywordTest() {
    final List<File> filesList = ResourceFinder.searchResource("2.0", "json", "capture", null);
    assertTrue(filesList.size() > 0);
  }

  @Test
  public void findResource20QueryDocumentTest() {
    final List<File> filesList = ResourceFinder.searchResource("2.0", "json", "query", null);
    assertTrue(filesList.size() > 0);
  }
}
