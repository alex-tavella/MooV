buildscript {

  repositories {
    jcenter()
    google()
  }

  dependencies {
    classpath(GradlePlugins.android)
    classpath(GradlePlugins.kotlin)
  }
}

plugins {
  id(GradlePlugins.gradleVersions).version(GradlePlugins.Versions.gradleversions)
}

allprojects {
  repositories {
    jcenter()
    google()
  }
}

tasks.register<Delete>("clean").configure {
  delete("build")
}
