plugins {
    id("lib-kotlin-android-no-config")
    id("publish-android")
}

dependencies {
    api(projects.requestPermissionApi)
    api(libs.common.coroutine)
}