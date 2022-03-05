plugins {
    id("lib-kotlin-android-no-config")
    id("publish-android")
}

dependencies {
    api(projects.bundleCollectorApi)

    implementation(libs.dagger.base)
    implementation(libs.androidx.compat)
}