package com.angdroid.refrigerator_manament.application

import android.app.Application
import android.content.Context
import com.angdroid.refrigerator_manament.BuildConfig
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        fireStoreUserReference = Firebase.firestore.collection("User").document(BuildConfig.USER_ID)
        fireStoreRecipeReference = Firebase.firestore.collection("Recipe")
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    companion object {
        lateinit var fireStoreUserReference: DocumentReference
        lateinit var fireStoreRecipeReference: CollectionReference
        private lateinit var instance: App
        fun getInstance(): Context {
            return instance.applicationContext
        }
    }
}