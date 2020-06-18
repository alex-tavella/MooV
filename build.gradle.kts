buildscript {

    repositories {
        jcenter()
        google()
    }

    dependencies {
        classpath(GradlePlugins.android)
        classpath(GradlePlugins.kotlin)
        classpath(GradlePlugins.hilt)
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
            licenseHeaderFile(project.rootProject.file("spotless/copyright.kt"))
            endWithNewline()
        }
        kotlinGradle {
            target("*.gradle.kts")
            ktlint(BuildPlugins.Versions.ktlint)
            endWithNewline()
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

    afterEvaluate {
        if (this.hasProperty("android")) {
            configure<com.android.build.gradle.BaseExtension> {
                compileSdkVersion(AndroidSdk.compileSdk)

                defaultConfig {
                    minSdkVersion(AndroidSdk.minSdk)
                    targetSdkVersion(AndroidSdk.targetSdk)

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                buildTypes {
                    getByName("release") {
                        isMinifyEnabled = false
                    }
                }

                compileOptions {
                    sourceCompatibility = AndroidSdk.javaVersion
                    targetCompatibility = AndroidSdk.javaVersion
                }

                packagingOptions {
                    exclude("META-INF/DEPENDENCIES")
                    exclude("META-INF/LICENSE")
                    exclude("META-INF/LICENSE.txt")
                    exclude("META-INF/license.txt")
                    exclude("META-INF/NOTICE")
                    exclude("META-INF/NOTICE.txt")
                    exclude("META-INF/notice.txt")
                    exclude("META-INF/ASL2.0")
                    exclude("META-INF/*.kotlin_module")
                }
            }
        }
    }
}
