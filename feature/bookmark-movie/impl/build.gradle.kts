plugins {
    id(BuildPlugins.android)
    id(BuildPlugins.kotlinKapt)
    id(BuildPlugins.coroutinesOptIn)
    id(GradlePlugins.anvil) version GradlePlugins.Versions.anvil
    id(BuildPlugins.quality)
}

android {
    defaultConfig {
        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
                arg("room.incremental", "true")
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

    // error when using implementation instead of api
    // e: Supertypes of the following classes cannot be resolved. Please make sure you have the required dependencies in the classpath:
    //     class br.com.bookmark.movie.data.local.BookmarksDatabase, unresolved supertypes: androidx.room.RoomDatabase
    api(Deps.room)
    implementation(Deps.roomKtx)
    kapt(Deps.roomCompiler)

    testImplementation(Deps.junit)

    androidTestImplementation(Deps.androidxRunner)
    androidTestImplementation(Deps.androidxEspressocore)
}
