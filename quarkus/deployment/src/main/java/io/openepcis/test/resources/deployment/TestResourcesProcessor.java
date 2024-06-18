package io.openepcis.test.resources.deployment;

import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.nativeimage.NativeImageResourcePatternsBuildItem;

public class TestResourcesProcessor {

  private static final String FEATURE = "openepcis-test-resources";


  @BuildStep
  FeatureBuildItem feature() {
    return new FeatureBuildItem(FEATURE);
  }

  @BuildStep
  NativeImageResourcePatternsBuildItem addNativeImageResourceBuildItem() {
    return NativeImageResourcePatternsBuildItem.builder().includeGlobs(
            "1.2/EPCIS/**",
            "2.0/EPCIS/**"
    ).build();
  }

}
