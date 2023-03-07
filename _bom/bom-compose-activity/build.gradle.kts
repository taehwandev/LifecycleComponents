plugins {
    id("publish-bom")
}

dependencies {

    api(projects.lifecycleHandlerAnnotations)
    api(projects.lifecycleHandlerApi)
    api(projects.resultHandlerApi)
    api(projects.bundleCollectorApi)
    api(projects.requestPermissionApi)
    api(projects.backKeyHandlerApi)
    api(projects.viewEventApi)
    api(projects.viewEventComposeExtensionApi)
    api(projects.routerApi)

    api(projects.resultHandlerFlowAdapter)
    api(projects.bundleCollectorFlowAdapter)
    api(projects.requestPermissionFlowHandler)
    api(projects.backKeyHandlerAdapterFlow)
    api(projects.viewEventAdapterFlow)

    api(projects.lifecycleHandlerComposeActivity)
    api(projects.lifecycleHandlerComposeActivityDagger)
    api(projects.daggerScope)

    api(libs.alatan.alerts.compose.dialog.api)

}