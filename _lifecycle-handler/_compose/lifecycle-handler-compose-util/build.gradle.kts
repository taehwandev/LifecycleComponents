plugins {
    id("lib-kotlin-android-no-config")
    id("publish-android")
    id("lib-compose")
}

dependencies {
    libs.versions.compose
    implementation(libs.compose.runtime)
    implementation(libs.compose.ui)
    implementation(libs.androidx.lifecycle.common)
    implementation(projects.lifecycleHandlerApi)
    implementation(projects.viewEventComposeExtensionApi)
}