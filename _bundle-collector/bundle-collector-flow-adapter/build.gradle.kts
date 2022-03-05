plugins {
    id("lib-kotlin-android-no-config")
    id("publish-android")
}

dependencies {
    api(libs.common.coroutine)
    api(projects.bundleCollectorApi)
    testImplementation(projects.bundleCollectorAssertion)
    testImplementation(projects.coroutineTestUtil)
}