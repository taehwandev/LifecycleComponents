plugins {
    id("lib-kotlin-android-no-config")
    id("publish-android")
}

dependencies {
    api(projects.viewEventApi)
    api(libs.common.coroutine)
}