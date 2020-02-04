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
  id(GradlePlugins.spotless).version(GradlePlugins.Versions.spotless)
  id(GradlePlugins.gradleVersions).version(GradlePlugins.Versions.gradleversions)
}

allprojects {
  repositories {
    jcenter()
    google()
  }
}

subprojects {
  apply(plugin = GradlePlugins.spotless)
  spotless {
    format("misc") {
      target("**/*.gradle", "**/*.md", "**/.gitignore")
      indentWithSpaces(2)
      trimTrailingWhitespace()
      endWithNewline()
    }
    kotlin {
      target("**/*.kt")
      ktlint(BuildPlugins.Versions.ktlint).userData(mapOf("indent_size" to "2", "continuation_indent_size" to "2"))
    }
    kotlinGradle {
      target("**/*.kt")
      ktlint(BuildPlugins.Versions.ktlint).userData(mapOf("indent_size" to "2", "continuation_indent_size" to "2"))
    }
  }

  tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class.java).all {
    kotlinOptions {
      // Treat all Kotlin warnings as errors
      allWarningsAsErrors = true

      // Enable experimental coroutines APIs, including Flow
      freeCompilerArgs + "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi"
      freeCompilerArgs + "-Xuse-experimental=kotlinx.coroutines.FlowPreview"
      freeCompilerArgs + "-Xuse-experimental=kotlinx.coroutines.FlowPreview"
      freeCompilerArgs + "-Xuse-experimental=kotlin.Experimental"

      jvmTarget = "1.8"
    }
  }
}
