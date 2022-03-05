plugins {
    id("lib-jvm")
    id("publish-jvm")
}

dependencies {
    api(projects.viewEventApi)

    compileOnly(libs.androidx.annotation)
    api(libs.androidx.collection)
}