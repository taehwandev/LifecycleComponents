plugins {
    id("lib-kotlin-android-no-config")
    id("lib-compose")
    id("publish-android")
}

dependencies {
    api(projects.lifecycleHandlerComposeUtil)
    api(projects.lifecycleHandlerApi)

    implementation(libs.compose.runtime)
    implementation(libs.compose.ui)
    implementation(libs.dagger.base)
    implementation(libs.androidx.lifecycle.common)
    implementation(projects.composableLifecycleListenerActivator)
}