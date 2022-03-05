plugins {
    id("lib-kotlin-android-no-config")
    id("lib-compose")
    id("publish-android")
}

dependencies {
    implementation(libs.compose.runtime)
    implementation(libs.androidx.annotation)
    implementation(projects.lifecycleHandlerApi)
    implementation(projects.lifecycleHandlerComposeUtil)
}