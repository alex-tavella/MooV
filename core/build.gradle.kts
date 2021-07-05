plugins {
    id(BuildPlugins.kotlin)
    id(BuildPlugins.quality)
}

dependencies {
    implementation(Deps.kotlinStdlib)
    implementation(Deps.inject)
}
