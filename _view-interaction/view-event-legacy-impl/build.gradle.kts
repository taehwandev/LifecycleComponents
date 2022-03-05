plugins {
    id("lib-kotlin-android-no-config")
    id("publish-android")
}

dependencies {
    api(projects.viewEventApi)

    compileOnly(libs.androidx.annotation)
    implementation(libs.androidx.collection)
    implementation(libs.androidx.compat)
    implementation(libs.androidx.databinding.runtime)
    implementation(libs.google.material)
}