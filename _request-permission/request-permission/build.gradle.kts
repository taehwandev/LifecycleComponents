plugins {
    id("lib-kotlin-android-no-config")
    id("publish-android")
}

dependencies {
    api(projects.requestPermissionApi)
    implementation(projects.lazyProvider)
    implementation(libs.androidx.compat)

    testImplementation(libs.test.powermock.core)
    testImplementation(libs.test.powermock.junit4)
    testImplementation(libs.test.powermock.mockito)
}