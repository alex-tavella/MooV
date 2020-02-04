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
            indentWithSpaces(4)
            trimTrailingWhitespace()
            endWithNewline()
        }
        kotlin {
            target("**/*.kt")
            ktlint(BuildPlugins.Versions.ktlint)
        }
        kotlinGradle {
            target("*.gradle.kts")
            ktlint(BuildPlugins.Versions.ktlint)
        }
    }

    tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class.java).all {
        kotlinOptions {
            // Treat all Kotlin warnings as errors
            allWarningsAsErrors = true

            // Enable experimental coroutines APIs, including Flow
            freeCompilerArgs + "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi"
            freeCompilerArgs + "-Xuse-experimental=kotlinx.coroutines.FlowPreview"
            freeCompilerArgs + "-Xuse-experimental=kotlin.Experimental"

            jvmTarget = "1.8"
        }
    }
}
