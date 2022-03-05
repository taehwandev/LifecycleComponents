plugins {
    id("lib-kotlin-android-no-config")
    id("lib-compose")
    id("publish-android")
}

dependencies {
    implementation(libs.compose.runtime)
    implementation(libs.dagger.base)
    implementation(projects.composableLifecycleListenerActivator)
    implementation(projects.lifecycleHandlerApi)
}