plugins {
    id(BuildPlugins.android)
    id(BuildPlugins.quality)
}

dependencies {
    implementation(Deps.kotlinStdlib)
    implementation(Deps.androidxFragmentKtx)
}
