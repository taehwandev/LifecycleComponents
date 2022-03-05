plugins {
    id("lib-kotlin-android-no-config")
    id("lib-compose")
    id("publish-android")
    kotlin("kapt")
}

dependencies {
    api(projects.lifecycleHandlerComposeActivity)
    api(projects.daggerBaseBuilder)

    implementation(libs.dagger.android)
    implementation(libs.dagger.baseCompiler)
    implementation(libs.google.material)

}