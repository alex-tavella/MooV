plugins {
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinKapt)
    id(BuildPlugins.coroutinesOptIn)
    id(GradlePlugins.anvil) version GradlePlugins.Versions.anvil
}

anvil {
    generateDaggerFactories.set(true)
}

dependencies {
    implementation(Deps.androidxActivityKtx)
    implementation(Deps.androidxFragmentKtx)
    implementation(Deps.androidxAppCompat)
    implementation(Deps.androidxLifecycle)
    implementation(Deps.androidxRecyclerview)
    implementation(Deps.coroutinesAndroid)
    implementation(Deps.kotlinStdlib)
    implementation(Deps.glide)
    kapt(Deps.glideCompiler)
    implementation(Deps.dagger)

    androidTestImplementation(Deps.androidxRunner)
    androidTestImplementation(Deps.androidxEspressocore)
}
