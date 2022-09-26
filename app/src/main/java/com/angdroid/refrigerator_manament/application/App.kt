package com.angdroid.refrigerator_manament.application

import android.app.Application
import com.angdroid.refrigerator_manament.BuildConfig
import timber.log.Timber

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}