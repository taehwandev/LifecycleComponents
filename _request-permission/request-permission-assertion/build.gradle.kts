plugins {
    id("lib-kotlin-android-no-config")
    id("publish-android")
}

dependencies {
    api(projects.requestPermissionApi)
    compileOnly(libs.androidx.annotation)
}