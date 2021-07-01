buildscript {

    repositories {
        mavenCentral()
        google()
    }

    dependencies {
        classpath(GradlePlugins.android)
        classpath(GradlePlugins.kotlin)
    }
}

plugins {
    id(GradlePlugins.spotless) version GradlePlugins.Versions.spotless
    id(GradlePlugins.gradleVersions) version GradlePlugins.Versions.gradleversions
    // id(GradlePlugins.gradleDoctor) version GradlePlugins.Versions.gradleDoctorVersion
    id(GradlePlugins.detekt) version GradlePlugins.Versions.detekt
}

allprojects {
    repositories {
        mavenCentral()
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

    apply(plugin = GradlePlugins.detekt)
    detekt {
        buildUponDefaultConfig = true

        reports {
            html.enabled = true
            xml.enabled = true
        }
    }

    tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
        // Target version of the generated JVM bytecode. It is used for type resolution.
        jvmTarget = "1.8"
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
        kotlinOptions {
            // Treat all Kotlin warnings as errors
            allWarningsAsErrors = true

            jvmTarget = "1.8"
        }
    }

    afterEvaluate {
        if (this.hasProperty("android")) {
            configure<com.android.build.gradle.BaseExtension> {
                compileSdkVersion(AndroidSdk.compileSdk)

                defaultConfig {
                    minSdk = AndroidSdk.minSdk
                    targetSdk = AndroidSdk.targetSdk

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
                    resources.excludes.addAll(
                        listOf(
                            "META-INF/DEPENDENCIES",
                            "META-INF/LICENSE",
                            "META-INF/LICENSE.txt",
                            "META-INF/license.txt",
                            "META-INF/NOTICE",
                            "META-INF/NOTICE.txt",
                            "META-INF/notice.txt",
                            "META-INF/ASL2.0",
                            "META-INF/*.kotlin_module",
                        )
                    )
                }
            }
        }
    }
}
