plugins {
    kotlin("jvm")
    id("code-quality")
    id("lib-tasks")
    id("publish-jvm")
}

dependencies {
    implementation(libs.common.kspProcessingApi)
    implementation(libs.common.kotlinPoet)
    api(projects.providedComposeLocalApi)
}