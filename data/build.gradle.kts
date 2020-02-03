plugins {
  id(BuildPlugins.androidLibrary)
  id(BuildPlugins.kotlinAndroid)
  id(BuildPlugins.kotlinKapt)
}

android {
  compileSdkVersion(AndroidSdk.compileSdk)

  defaultConfig {
    minSdkVersion(AndroidSdk.minSdk)
    targetSdkVersion(AndroidSdk.targetSdk)
    versionCode = 1
    versionName = "1.0.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    javaCompileOptions {
      annotationProcessorOptions {
        arguments["room.schemaLocation"] = "$projectDir/schemas"
      }
    }
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
}

dependencies {
  implementation(project(Modules.domain))

  implementation(Deps.okHttp)

  // Retrofit
  implementation(Deps.retrofit)
  implementation(Deps.gsonRetrofitConverter)
  implementation(Deps.okHttpLoggingInterceptor)

  // Kotlin
  implementation(Deps.kotlinStdlib)
  implementation(Deps.coroutines)

  // DI
  implementation(Deps.dagger)
  kapt(Deps.daggerCompiler)

  // Room
  implementation(Deps.room)
  kapt(Deps.roomCompiler)

  testImplementation(Deps.retrofitMock)
  testImplementation(Deps.mockitoKotlin)
  testImplementation(Deps.junit)
  testImplementation(Deps.assertj)
  testImplementation(project(path = Modules.domain, configuration = "testArtifacts"))

  androidTestImplementation(Deps.androidxRunner)
  androidTestImplementation(Deps.androidxEspressocore)
}
