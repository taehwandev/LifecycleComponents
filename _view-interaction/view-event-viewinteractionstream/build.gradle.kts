plugins {
    id("lib-jvm")
    id("publish-jvm")
}

dependencies {
    api(projects.viewEventApi)
    implementation(libs.androidx.annotation)
}