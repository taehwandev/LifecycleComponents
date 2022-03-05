plugins {
    id("lib-kotlin-android-no-config")
    kotlin("kapt")
    id("publish-android")
}

android {
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(projects.lifecycleHandlerImpl)
    implementation(projects.resultHandler)
    implementation(projects.bundleCollector)
    implementation(projects.router)
    implementation(projects.viewEventLegacyImpl)
    implementation(projects.viewEventViewinteractionstream)
    implementation(libs.alatan.bundledata.impl)
    implementation(libs.alatan.resourceprovider.impl)
    implementation(libs.alatan.alerts.common.dialog.impl)
    implementation(projects.lifecycleHandlerInvokeadapterApi)
    implementation(projects.lazyProvider)
    implementation(projects.backKeyHandler)
    implementation(projects.requestPermission)
    implementation(libs.alatan.coroutine.dispatcher.api)

    api(projects.lifecycleHandlerApi)
    api(projects.resultHandlerApi)
    api(projects.bundleCollectorApi)
    api(libs.alatan.bundledata.api)
    api(projects.routerApi)
    api(projects.viewEventApi)
    api(libs.alatan.alerts.common.dialog.api)
    api(projects.coroutineApi)
    api(projects.backKeyHandlerApi)
    api(projects.requestPermissionApi)

    api(libs.androidx.compat)
    api(libs.androidx.lifecycle.viewmodel)
    api(libs.androidx.lifecycle.service)
    implementation(libs.androidx.lifecycle.jdk8)
    implementation(libs.google.material)

}