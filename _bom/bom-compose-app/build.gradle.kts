plugins {
    id("publish-bom")
}

dependencies {
    api(projects.lifecycleHandlerImpl)
    api(projects.resultHandler)
    api(projects.bundleCollector)
    api(projects.requestPermission)
    api(projects.backKeyHandler)
    api(projects.viewEventComposeImpl)
    api(projects.router)

    api(projects.composableLifecycleListenerActivator)
    api(projects.lifecycleHandlerInvokeadapterApi)
    api(projects.lifecycleHandlerAnnotations)
    api(projects.composableHolder)


    api(projects.lifecycleHandlerInvokeadapterFlow)
    api(projects.resultHandlerFlowAdapter)
    api(projects.bundleCollectorFlowAdapter)
    api(projects.requestPermissionFlowHandler)
    api(projects.backKeyHandlerAdapterFlow)
    api(projects.viewEventAdapterFlow)

    api(projects.lazyProvider)
    api(projects.lifecycleHandlerComposeActivity)
    api(projects.lifecycleHandlerComposeActivityDagger)
    api(projects.daggerBaseBuilder)
    api(projects.daggerScope)

    api(projects.coroutineApi)
    api(libs.alatan.alerts.compose.dialog.api)
    api(libs.alatan.alerts.compose.dialog.impl)
}