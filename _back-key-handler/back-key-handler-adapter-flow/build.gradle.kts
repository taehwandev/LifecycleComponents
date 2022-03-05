plugins {
    id("lib-jvm")
    id("publish-jvm")
}

dependencies {

    api(projects.backKeyHandlerApi)
    api(libs.common.coroutine)

    testImplementation(projects.backKeyHandlerAssertion)
    testImplementation(projects.coroutineTestUtil)
}