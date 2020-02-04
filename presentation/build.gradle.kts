plugins {
    id(BuildPlugins.androidApplication)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinKapt)
}

android {
    compileSdkVersion(AndroidSdk.compileSdk)

    defaultConfig {
        applicationId = "br.com.moov.app"
        minSdkVersion(AndroidSdk.minSdk)
        targetSdkVersion(AndroidSdk.targetSdk)
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = AndroidSdk.javaVersion
        targetCompatibility = AndroidSdk.javaVersion
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    implementation(project(Modules.data))
    implementation(project(Modules.domain))
    implementation(Deps.androidxAppCompat)
    implementation(Deps.androidxMaterial)
    implementation(Deps.androidxConstraintlayout)
    implementation(Deps.androidxLifecycle)
    implementation(Deps.coroutinesAndroid)
    implementation(Deps.kotlinStdlib)
    implementation(Deps.glide)
    kapt(Deps.glideCompiler)
    implementation(Deps.dagger)
    kapt(Deps.daggerCompiler)
    implementation(Deps.okHttp)
    implementation(Deps.retrofit)

    testImplementation(Deps.mockitoKotlin)
    testImplementation(Deps.junit)
    testImplementation(Deps.assertj)
    testImplementation(project(path = Modules.domain, configuration = "testArtifacts"))

    androidTestImplementation(Deps.androidxRunner)
    androidTestImplementation(Deps.androidxEspressocore)
}
