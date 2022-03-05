plugins {
    id("lib-kotlin-android-no-config")
    id("publish-android")
}

dependencies {

    api(projects.resultHandlerApi)
    api(libs.alatan.bundledata.api)
    implementation(libs.alatan.bundledata.impl)

    testImplementation(projects.resultHandlerAssertion)
    testImplementation(libs.alatan.bundledata.assertion)
}