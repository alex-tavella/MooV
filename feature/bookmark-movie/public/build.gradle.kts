plugins {
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.kotlinAndroid)
}

dependencies {
    implementation(Deps.kotlinStdlib)
    implementation(project(Modules.core))
}
