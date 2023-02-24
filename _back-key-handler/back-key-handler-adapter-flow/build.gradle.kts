plugins {
    id("lib-jvm")
    id("publish-jvm")
}

dependencies {

    api(projects.backKeyHandlerApi)
    api(libs.common.coroutine)

    testImplementation(projects.backKeyHandlerAssertion)
    testImplementation(libs.test.turbine)
    testImplementation(libs.test.coroutineTest)

}