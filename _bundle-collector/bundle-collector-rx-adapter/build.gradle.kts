plugins {
    id("lib-kotlin-android-no-config")
    id("publish-android")
}

dependencies {
    api(libs.common.rxjava)
    api(projects.bundleCollectorApi)
    testImplementation(projects.bundleCollectorAssertion)
}