plugins {
    id("lib-jvm")
    id("publish-jvm")
}

dependencies {
    api(projects.backKeyHandlerApi)
    api(libs.common.rxjava)

    testImplementation(projects.backKeyHandlerAssertion)
}