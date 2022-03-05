plugins {
    id("lib-kotlin-android-no-config")
    id("lib-compose")
    id("publish-android")
}

dependencies {
    implementation(libs.compose.ui)
    compileOnly(libs.androidx.annotation)
}