plugins {
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinKapt)
}

dependencies {
    implementation(project(Modules.core))
    implementation(Deps.kotlinStdlib)
    implementation(Deps.coroutines)
    implementation(Deps.okHttp)
    implementation(Deps.retrofit)
    implementation(Deps.gsonRetrofitConverter)
    implementation(Deps.okHttpLoggingInterceptor)
    implementation(Deps.dagger)
    kapt(Deps.daggerCompiler)
}
