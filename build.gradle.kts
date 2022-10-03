// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath(Plugins.HILT_PLUGIN)
        classpath(Plugins.SAFE_ARGS_PLUGIN)
        classpath("com.google.gms:google-services:4.3.13")
    }
}
plugins {
    id("com.android.application") version "7.3.0" apply false
    id("com.android.library") version "7.3.0" apply false
    id("org.jetbrains.kotlin.android") version "1.7.10" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}