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
    versionName ="1.0.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    getByName("release") {
      isMinifyEnabled = false
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
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
  implementation(Deps.koinAndroidxViewModel)
  implementation(Deps.coroutinesAndroid)
  implementation(Deps.kotlinStdlib)
  implementation(Deps.glide)
  kapt(Deps.glideCompiler)

  testImplementation(Deps.mockitoKotlin)
  testImplementation(Deps.junit)
  testImplementation(Deps.assertj)
  testImplementation(project(path = Modules.domain, configuration = "testArtifacts"))

  androidTestImplementation(Deps.androidxRunner)
  androidTestImplementation(Deps.androidxEspressocore)
}
