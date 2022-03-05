plugins {
    id("lib-kotlin-android-no-config")
    id("publish-android")
}

dependencies {
    compileOnly(libs.androidx.annotation)
    implementation(libs.common.coroutine)
    implementation(libs.alatan.coroutine.dispatcher.api)

    testImplementation(libs.alatan.coroutine.dispatcher.assertion)
}