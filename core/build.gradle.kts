plugins {
    id(BuildPlugins.kotlin)
}

setProperty("targetCompatibility", AndroidSdk.javaVersion)
setProperty("sourceCompatibility", AndroidSdk.javaVersion)

dependencies {
    implementation(Deps.kotlinStdlib)
}
