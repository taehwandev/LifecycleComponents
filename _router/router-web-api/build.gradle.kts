plugins {
    id("lib-kotlin-android-no-config")
    id("publish-android")
}

dependencies {
    api(projects.routerApi)
    api(projects.bundleCollectorApi)
}