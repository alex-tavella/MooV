plugins {
    id(BuildPlugins.android)
    id(BuildPlugins.kotlinKapt)
    id(BuildPlugins.coroutinesOptIn)
    id(GradlePlugins.anvil) version GradlePlugins.Versions.anvil
    id(BuildPlugins.quality)
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.coreAndroid))
    implementation(project(Modules.FeatureApis.movieDetails))
    implementation(project(Modules.FeatureApis.bookmarkMovie))
    implementation(Deps.androidxActivityKtx)
    implementation(Deps.androidxFragmentKtx)
    implementation(Deps.androidxAppCompat)
    implementation(Deps.androidxMaterial)
    implementation(Deps.androidxConstraintlayout)
    implementation(Deps.androidxLifecycle)
    implementation(Deps.coroutinesAndroid)
    implementation(Deps.kotlinStdlib)
    implementation(Deps.dagger)
    kapt(Deps.daggerCompiler)
    implementation(Deps.retrofit)
    implementation(Deps.moshi)
    kapt(Deps.moshiCompiler)
    implementation(Deps.glide)

    testImplementation(Deps.junit)
}
