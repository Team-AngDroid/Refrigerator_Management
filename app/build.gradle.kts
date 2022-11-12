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



    // Object detection feature with bundled default classifier
    implementation("com.google.mlkit:object-detection:17.0.0")
    // Object detection feature with custom classifier support
    implementation("com.google.mlkit:object-detection-custom:17.0.0")
    // On Device Machine Learnings
    implementation("com.google.android.odml:image:1.0.0-beta1")

    // CameraX
    implementation ("androidx.camera:camera-camera2:1.0.0-SNAPSHOT")
    implementation ("androidx.camera:camera-lifecycle:1.0.0-SNAPSHOT")
    implementation ("androidx.camera:camera-view:1.0.0-SNAPSHOT")

    // ViewModel and LiveData
    implementation ("androidx.lifecycle:lifecycle-livedata:2.3.1")
    implementation ("androidx.lifecycle:lifecycle-viewmodel:2.3.1")

    // Selfie segmentation
    implementation ("com.google.mlkit:segmentation-selfie:16.0.0-beta4")
    implementation ("com.google.mlkit:camera:16.0.0-beta3")
    // Image labeling custom
    implementation("com.google.mlkit:image-labeling-custom:17.0.1")
    //    // Or comment the dependency above and uncomment the dependency below to
    //    // use unbundled model that depends on Google Play Services
    //    // implementation 'com.google.android.gms:play-services-mlkit-image-labeling-custom:16.0.0-beta4'

    implementation("com.google.code.gson:gson:2.8.6")
    implementation("com.google.guava:guava:27.1-android")
    // Image labeling
    implementation("com.google.mlkit:image-labeling:17.0.7")
    implementation("com.google.firebase:firebase-messaging-ktx:23.0.0")
}