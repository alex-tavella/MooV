plugins {
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinKapt)
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.coreAndroid))
    implementation(project(Modules.FeatureApis.bookmarkMovie))
    implementation(Deps.coroutinesAndroid)
    implementation(Deps.kotlinStdlib)
    implementation(Deps.dagger)
    kapt(Deps.daggerCompiler)

    // Room
    implementation(Deps.room)
    implementation(Deps.roomKtx)
    kapt(Deps.roomCompiler)

    testImplementation(Deps.mockitoKotlin)
    testImplementation(Deps.junit)
    testImplementation(Deps.assertj)

    androidTestImplementation(Deps.androidxRunner)
    androidTestImplementation(Deps.androidxEspressocore)
}
