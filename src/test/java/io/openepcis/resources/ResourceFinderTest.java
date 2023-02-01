package io.openepcis.resources;

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
    final List<File> filesList = ResourceFinder.searchEPCISDocument("1.2", "xml", "ObjectEvent");
    assertTrue(filesList.size() > 0);
  }

  @Test
  public void findResource12WithVersionTest() {
    final List<File> filesList = ResourceFinder.searchEPCISDocument("1.2", "xml", null);
    assertTrue(filesList.size() > 0);
  }

  @Test
  public void findResource20WithVersionTest() {
    final List<File> filesList = ResourceFinder.searchEPCISDocument("1.2", "json", null);
    assertTrue(filesList.size() > 0);
  }

  @Test
  public void findResource20WithVersionKeywordTest() {
    final List<File> filesList = ResourceFinder.searchEPCISDocument("2.0", "json", "error");
    assertTrue(filesList.size() > 0);
  }
}
