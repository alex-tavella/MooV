import org.gradle.api.JavaVersion

private const val kotlinVersion = "1.3.61"

object GradlePlugins {
    object Versions {
        const val gradleandroid = "3.5.3"//4.0.0-alpha09"
        const val gradleversions = "0.27.0"
        const val spotless = "3.27.1"
    }

    const val android = "com.android.tools.build:gradle:${Versions.gradleandroid}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
    const val gradleVersions = "com.github.ben-manes.versions"
    const val spotless = "com.diffplug.gradle.spotless"
}

object BuildPlugins {
    object Versions {
        const val ktlint = "0.35.0"
    }

    const val androidApplication = "com.android.application"
    const val androidLibrary = "com.android.library"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinKapt = "kotlin-kapt"
    const val kotlin = "kotlin"
}

object AndroidSdk {
    const val minSdk = 21
    const val compileSdk = 29
    const val targetSdk = 29
    val javaVersion = JavaVersion.VERSION_1_8
    const val buildTools = "29.0.0"
}

object Deps {
    object Versions {
        const val androidx_core = "1.2.0-beta02"
        const val androidx_appcompat = "1.2.0-alpha02"
        const val androidx_viewmodel = "2.1.0-beta01"
        const val androidx_lifecycle = "2.2.0"
        const val androidx_recyclerview = "1.2.0-alpha01"
        const val androidx_constraintLayout = "2.0.0-beta4"
        const val androidx_activityKtx = "1.1.0"
        const val androidx_fragmentKtx = "1.2.1"
        const val material = "1.2.0-alpha04"
        const val play_core_ktx = "1.6.4"
        const val coroutines = "1.3.2"
        const val dagger = "2.26"
        const val retrofit = "2.7.1"
        const val okhttp = "4.3.1"
        const val room = "2.2.3"
        const val glide = "4.11.0"
        const val timber = "4.7.1"
        const val moshi = "1.9.2"

        const val junit = "4.13"
        const val mockk = "1.9.3"
        const val kotest = "3.4.2"
        const val androidx_espresso = "3.3.0-alpha03"
        const val androidx_testing = "1.1.1"
        const val assertj = "3.15.0"
        const val test_runner = "1.3.0-alpha03"
        const val mockito_kotlin = "2.1.0"
    }

    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion"

    const val androidxCore = "androidx.core:core-ktx:${Versions.androidx_core}"
    const val androidxAppCompat = "androidx.appcompat:appcompat:${Versions.androidx_appcompat}"
    const val androidxLifecycle =
        "androidx.lifecycle:lifecycle-extensions:${Versions.androidx_lifecycle}"
    const val androidxActivityKtx =
        "androidx.activity:activity-ktx:${Versions.androidx_activityKtx}"
    const val androidxFragmentKtx =
        "androidx.fragment:fragment-ktx:${Versions.androidx_fragmentKtx}"
    const val androidxViewmodelKtx =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.androidx_viewmodel}"
    const val androidxConstraintlayout =
        "androidx.constraintlayout:constraintlayout:${Versions.androidx_constraintLayout}"
    const val androidxMaterial = "com.google.android.material:material:${Versions.material}"
    const val androidxRecyclerview =
        "androidx.recyclerview:recyclerview:${Versions.androidx_recyclerview}"
    const val playCoreKtx = "com.google.android.play:core-ktx:${Versions.play_core_ktx}"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val room = "androidx.room:room-runtime:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val okHttpLoggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val moshiRetrofitConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    const val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
    const val moshiCompiler = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    const val junit = "junit:junit:${Versions.junit}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val kotest = "io.kotlintest:kotlintest-runner-junit5:${Versions.kotest}"
    const val retrofitMock = "com.squareup.retrofit2:retrofit-mock:${Versions.retrofit}"
    const val mockitoKotlin =
        "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockito_kotlin}"
    const val assertj = "org.assertj:assertj-core:${Versions.assertj}"
    const val androidxRules = "androidx.test:rules:${Versions.androidx_testing}"
    const val androidxRunner = "androidx.test:runner:${Versions.test_runner}"
    const val androidxEspressocore =
        "androidx.test.espresso:espresso-core:${Versions.androidx_espresso}"
}

object Modules {
    const val core = ":core"
    const val coreAndroid = ":core-android"
    const val diBridge = ":di-bridge"

    object Features {
        const val home = ":feature:home"
        const val movies = ":feature:movies"
        const val movieDetails = ":feature:movie-details"
        const val bookmarkMovie = ":feature:bookmark-movie"
    }

    object FeatureApis {
        const val movies = ":feature-api:movies"
        const val movieDetails = ":feature-api:movie-details"
        const val bookmarkMovie = ":feature-api:bookmark-movie"
    }

    object CoreLibs {
        const val tmdb = ":core-lib:tmdb"
    }
}
