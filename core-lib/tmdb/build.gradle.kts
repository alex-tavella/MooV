plugins {
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinKapt)
    id(GradlePlugins.anvil) version GradlePlugins.Versions.anvil
}

anvil {
    generateDaggerFactories.set(true)
}

dependencies {
    implementation(project(Modules.core))
    implementation(Deps.kotlinStdlib)
    implementation(Deps.coroutines)
    implementation(Deps.okHttp)
    implementation(Deps.retrofit)
    implementation(Deps.moshiRetrofitConverter)
    implementation(Deps.okHttpLoggingInterceptor)
    implementation(Deps.moshi)
    kapt(Deps.moshiCompiler)
    implementation(Deps.dagger)
}
