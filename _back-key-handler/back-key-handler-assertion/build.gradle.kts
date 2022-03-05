plugins {
    id("lib-jvm")
    id("publish-jvm")
}

dependencies {
    api(projects.backKeyHandlerApi)
    compileOnly(libs.androidx.annotation)
}