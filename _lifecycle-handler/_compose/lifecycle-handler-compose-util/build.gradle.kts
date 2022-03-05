plugins {
    id("lib-kotlin-android-no-config")
    id("publish-android")
    id("lib-compose")
}

dependencies {
    libs.versions.compose
    implementation(libs.compose.runtime)
    implementation(projects.lifecycleHandlerApi)
    implementation(projects.viewEventComposeExtensionApi)
}