plugins {
    id("lib-kotlin-android-no-config")
    id("publish-android")
}

dependencies {
    implementation(libs.androidx.activity)
    implementation(projects.backKeyHandlerApi)

    testImplementation(libs.test.powermock.core)
    testImplementation(libs.test.powermock.junit4)
    testImplementation(libs.test.powermock.mockito)

}