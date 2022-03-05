plugins {
    id("lib-kotlin-android-no-config")
    id("publish-android")
}

dependencies {
    api(projects.resultHandlerApi)
    api(libs.alatan.bundledata.assertion)
}