plugins {
    id(BuildPlugins.android)
    id(BuildPlugins.quality)
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.coreAndroid))
    implementation(project(Modules.FeatureApis.movies))
    implementation(Deps.androidxAppCompat)
    implementation(Deps.androidxMaterial)
    implementation(Deps.kotlinStdlib)
}
