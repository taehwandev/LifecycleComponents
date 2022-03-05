plugins {
    id("lib-kotlin-android-no-config")
    id("publish-android")
}

dependencies {
    api(projects.bundleCollectorApi)
    api(projects.bundleCollectorAssertion)
    api(projects.routerApi)

    implementation(libs.androidx.compat)
    implementation(libs.test.mockito)
    implementation(libs.test.mockitoKotlin)
}