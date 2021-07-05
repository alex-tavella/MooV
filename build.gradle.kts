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
    id(GradlePlugins.spotless)
    id(GradlePlugins.gradleVersions) version GradlePlugins.Versions.gradleversions
    // id(GradlePlugins.gradleDoctor) version GradlePlugins.Versions.gradleDoctorVersion
    id(GradlePlugins.detekt)
}

allprojects {
    repositories {
        mavenCentral()
        google()
    }
}
