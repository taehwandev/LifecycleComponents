plugins {
    id("lib-kotlin-android-no-config")
    id("publish-android")
}

dependencies {
    api(projects.viewEventComposeExtensionApi)
    implementation(libs.compose.ui)
    compileOnly(libs.androidx.annotation)
}