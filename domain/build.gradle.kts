plugins {
  id(BuildPlugins.kotlin)
}

tasks.register<Jar>("testJar") {
  from(sourceSets["test"].output)
  archiveClassifier.set("test")
}

configurations {
  register("testArtifacts")
}

artifacts {
  add("testArtifacts", tasks.getByName("testJar"))
}

setProperty("targetCompatibility", JavaVersion.VERSION_1_8)
setProperty("sourceCompatibility", JavaVersion.VERSION_1_8)

dependencies {
  implementation(Deps.koin)
  implementation(Deps.coroutines)

  testImplementation(Deps.junit)
  testImplementation(Deps.mockitoKotlin)
  testImplementation(Deps.assertj)
}
