plugins {
    id("lib-jvm")
    id("code-quality")
    id("publish-jvm")
    id("com.android.lint")
}

dependencies {
    compileOnly(libs.androidLint.api)

    testImplementation(libs.androidLint.impl)
    testImplementation(libs.androidLint.test)
    testImplementation(libs.test.junit4)
}