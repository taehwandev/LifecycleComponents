plugins {
    id("lib-kotlin-android-no-config")
    id("lib-compose")
    id("publish-android")
}

dependencies {
    implementation(projects.lifecycleHandlerImpl)
    implementation(projects.viewEventComposeImpl)
    implementation(projects.viewEventViewinteractionstream)
    implementation(projects.viewEventComposeExtensionImpl)
    implementation(projects.resultHandler)
    implementation(projects.bundleCollector)
    implementation(projects.router)
    implementation(projects.composableLifecycleListenerActivator)
    implementation(projects.lazyProvider)
    implementation(projects.backKeyHandler)
    implementation(projects.requestPermission)
    implementation(libs.alatan.alerts.compose.dialog.impl)
    implementation(libs.alatan.bundledata.impl)
    implementation(libs.alatan.resourceprovider.impl)
    implementation(libs.alatan.coroutine.dispatcher.api)

    api(projects.viewEventApi)
    api(projects.lifecycleHandlerAnnotations)
    api(projects.lifecycleHandlerApi)
    api(projects.bundleCollectorApi)
    api(projects.routerApi)
    api(projects.resultHandlerApi)
    api(projects.backKeyHandlerApi)
    api(projects.lifecycleHandlerComposeUtil)
    api(projects.viewEventComposeExtensionApi)
    api(projects.requestPermissionApi)
    api(libs.alatan.bundledata.api)
    api(libs.alatan.alerts.compose.dialog.api)

    api(libs.androidx.compat)
    api(libs.androidx.lifecycle.viewmodel)
    api(libs.androidx.lifecycle.service)
    implementation(libs.androidx.lifecycle.jdk8)
    implementation(libs.google.material)
    implementation(libs.androidx.composeactivity)

}