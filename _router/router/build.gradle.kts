plugins {
    id("lib-kotlin-android-no-config")
    id("lib-kotlin-robolectric")
    id("publish-android")
}

dependencies {

    api(projects.routerApi)
    implementation(projects.lazyProvider)
    implementation(libs.alatan.bundledata.impl)
    api(libs.androidx.compat)

    testImplementation(libs.test.powermock.core)
    testImplementation(libs.test.powermock.junit4)
    testImplementation(libs.test.powermock.mockito)
}