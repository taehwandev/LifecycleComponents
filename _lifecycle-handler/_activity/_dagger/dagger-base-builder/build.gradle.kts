plugins {
    id("lib-kotlin-android-no-config")
    id("publish-android")
}

dependencies {
    implementation(libs.common.javaX)
    implementation(libs.dagger.android)
    implementation(libs.alatan.resourceprovider.impl)
    implementation(libs.alatan.alerts.common.dialog.impl)
}