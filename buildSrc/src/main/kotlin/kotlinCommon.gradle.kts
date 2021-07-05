plugins {
    id("kotlin")
}

setProperty("targetCompatibility", AndroidSdk.javaVersion)
setProperty("sourceCompatibility", AndroidSdk.javaVersion)

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
    kotlinOptions {
        // Treat all Kotlin warnings as errors
        allWarningsAsErrors = true

        jvmTarget = "1.8"
    }
}
