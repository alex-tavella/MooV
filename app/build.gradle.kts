plugins {
    id(BuildPlugins.androidApplication)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinKapt)
    id(GradlePlugins.anvil) version GradlePlugins.Versions.anvil
}

android {
    defaultConfig {
        applicationId = "br.com.moov"
        versionCode = 1
        versionName = "1.0.0"
    }
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.coreAndroid))

    implementation(Deps.dagger)
    kapt(Deps.daggerCompiler)

    implementation(Deps.retrofit)

    implementation(project(Modules.FeatureApis.movies))
    implementation(project(Modules.FeatureApis.movieDetails))
    implementation(project(Modules.FeatureApis.bookmarkMovie))

    implementation(project(Modules.Features.home))
    implementation(project(Modules.Features.movies))
    implementation(project(Modules.Features.movieDetails))
    implementation(project(Modules.Features.bookmarkMovie))

    implementation(project(Modules.CoreLibs.tmdb))
}
