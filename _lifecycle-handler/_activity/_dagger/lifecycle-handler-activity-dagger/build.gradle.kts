plugins {
    id("lib-kotlin-android-no-config")
    id("publish-android")
    kotlin("kapt")
}

android {
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    api(projects.lifecycleHandlerActivity)
    api(projects.daggerBaseBuilder)
    api(libs.google.material)

    implementation(libs.dagger.android)
    kapt(libs.dagger.baseCompiler)
}