import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
}

val properties = Properties()
properties.load(project.rootProject.file("local.properties").inputStream())

android {
    namespace = DefaultConfig.APPLICATION_ID
    compileSdk = DefaultConfig.COMPILE_SDK

    defaultConfig {
        applicationId = DefaultConfig.APPLICATION_ID
        minSdk = DefaultConfig.MIN_SDK
        targetSdk = DefaultConfig.TARGET_SDK
        versionCode = DefaultConfig.VERSION_CODE
        versionName = DefaultConfig.VERSION_NAME

        testInstrumentationRunner = DefaultConfig.TEST_INSTRUMENTATION_RUNNER
        buildConfigField("String", "USER_ID", properties.getProperty("USER_ID"))
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile ("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = DefaultConfig.JVM_TARGET
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    addFireBaseDependencies()
    addAndroidXDependencies()
    addNetworkDependencies()
    addNavigationDependencies()
    addDaggerHiltDependencies()
    addLifecycleDependencies()
    addRoomDependencies()
    addTestDependencies()
    implementation(AndroidXDependencies.lifecycleJava8)
    implementation(AndroidXDependencies.window)
    implementation(ThirdPartyDependencies.timber)
    implementation(ThirdPartyDependencies.coil)
    implementation(ThirdPartyDependencies.lottie)
    implementation(AndroidXDependencies.coroutinePlayService)
    implementation(KotlinDependencies.coroutines)
    implementation(AndroidXDependencies.coroutines)
    implementation(ThirdPartyDependencies.gson)
    implementation(KotlinDependencies.kotlinxSerialization)

}