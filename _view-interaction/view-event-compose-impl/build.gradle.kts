plugins {
    id("lib-kotlin-android-no-config")
    id("publish-android")
}

dependencies {
    api(projects.viewEventApi)
    api(projects.viewEventComposeExtensionApi)

    compileOnly(libs.androidx.annotation)
    implementation(libs.androidx.collection)

}