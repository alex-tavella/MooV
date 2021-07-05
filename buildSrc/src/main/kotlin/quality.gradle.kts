plugins {
    id("com.diffplug.spotless")
    id("io.gitlab.arturbosch.detekt")
}

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
