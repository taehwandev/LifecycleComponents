plugins {
    id("lib-kotlin-android-no-config")
    id("publish-android")
}

dependencies {
    api(libs.common.coroutine)
    api(libs.androidx.lifecycle.runtimeKtx)
    api(projects.lifecycleHandlerInvokeadapterApi)

    testImplementation(libs.test.coroutineTest)
    testImplementation(libs.common.rxjava)
}