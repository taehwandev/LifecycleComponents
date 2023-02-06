plugins {
    id("lib-kotlin-android-no-config")
    id("publish-android")
}

dependencies {
    api(projects.lifecycleHandlerApi)
    api(projects.lifecycleHandlerAnnotations)
    api(projects.lifecycleHandlerInvokeadapterApi)

    implementation(libs.androidx.annotation)
    implementation(libs.common.rxjava)
    implementation(libs.common.coroutine)
    implementation(libs.alatan.coroutine.dispatcher.api)
    implementation(projects.coroutineApi)

    testImplementation(projects.lifecycleHandlerInvokeadapterFlow)
    testImplementation(projects.viewEventViewinteractionstream)
    testImplementation(projects.lifecycleHandlerInvokeadapterRx)
    testImplementation(projects.lifecycleHandlerAssertion)
    testImplementation(libs.alatan.coroutine.dispatcher.assertion)
}