buildscript {

  repositories {
    jcenter()
    google()
  }

  dependencies {
        classpath(GradlePlugins.android)
        classpath(GradlePlugins.kotlin)
        classpath(GradlePlugins.gradleVersions)
  }
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
