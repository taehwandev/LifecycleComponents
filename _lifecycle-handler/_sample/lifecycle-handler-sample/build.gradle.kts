plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = libs.versions.androidCompile.get().toInt()
    buildToolsVersion = libs.versions.androidBuildTool.get()

    defaultConfig {
        applicationId = "io.androidalatan.lifecycle.handler.sample"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {

    implementation(libs.androidx.compat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.common.moshi)

    implementation(libs.google.material)

    implementation(libs.alatan.coroutine.dispatcher.api)
    implementation(libs.alatan.bundledata.impl)
    implementation(libs.alatan.databinding.impl)
    implementation(libs.alatan.preferences.api)
    implementation(libs.alatan.preferences.annotation)
    implementation(libs.alatan.preferences.builder)
    implementation(libs.alatan.preferences.flow)

    implementation(projects.lifecycleHandlerImpl)
    implementation(projects.lifecycleHandlerActivity)
    implementation(projects.resultHandler)
    implementation(projects.resultHandlerFlowAdapter)
    implementation(projects.bundleCollector)
    implementation(projects.bundleCollectorFlowAdapter)
    implementation(projects.requestPermission)
    implementation(projects.requestPermissionFlowHandler)
    implementation(projects.backKeyHandler)
    implementation(projects.backKeyHandlerAdapterFlow)
    implementation(projects.viewEventAdapterFlow)
    implementation(projects.lifecycleHandlerInvokeadapterFlow)

}

