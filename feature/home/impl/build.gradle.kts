plugins {
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinKapt)
    id(BuildPlugins.hilt)
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.coreAndroid))
    implementation(project(Modules.FeatureApis.movies))
    implementation(Deps.androidxAppCompat)
    implementation(Deps.androidxMaterial)
    implementation(Deps.kotlinStdlib)
    implementation(Deps.hilt)
    kapt(Deps.hiltCompiler)
}
