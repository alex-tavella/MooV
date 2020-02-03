plugins {
  id(BuildPlugins.kotlin)
  id(BuildPlugins.kotlinKapt)
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

setProperty("targetCompatibility", AndroidSdk.javaVersion)
setProperty("sourceCompatibility", AndroidSdk.javaVersion)

dependencies {
  implementation(Deps.dagger)
  kapt(Deps.daggerCompiler)
  implementation(Deps.coroutines)

  testImplementation(Deps.junit)
  testImplementation(Deps.mockitoKotlin)
  testImplementation(Deps.assertj)
}
