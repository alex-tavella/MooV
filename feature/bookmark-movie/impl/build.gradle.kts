plugins {
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinKapt)
    id(BuildPlugins.hilt)
}

kapt {
    correctErrorTypes = true
}

android {
    defaultConfig {
        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true"
                )
            }
        }
    }
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.coreAndroid))
    implementation(project(Modules.FeatureApis.bookmarkMovie))
    implementation(Deps.coroutinesAndroid)
    implementation(Deps.kotlinStdlib)
    implementation(Deps.dagger)
    kapt(Deps.daggerCompiler)
    implementation(Deps.hilt)
    kapt(Deps.hiltCompiler)

    // error when using implementation instead of api
    // e: Supertypes of the following classes cannot be resolved. Please make sure you have the required dependencies in the classpath:
    //     class br.com.bookmark.movie.data.local.BookmarksDatabase, unresolved supertypes: androidx.room.RoomDatabase
    api(Deps.room)
    implementation(Deps.roomKtx)
    kapt(Deps.roomCompiler)

    testImplementation(Deps.mockitoKotlin)
    testImplementation(Deps.junit)
    testImplementation(Deps.assertj)

    androidTestImplementation(Deps.androidxRunner)
    androidTestImplementation(Deps.androidxEspressocore)
}
