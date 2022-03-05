plugins {
    id("lib-kotlin-android-no-config")
    id("publish-android")
}

dependencies {
    api(projects.bundleCollectorApi)
    compileOnly(libs.androidx.annotation)
}