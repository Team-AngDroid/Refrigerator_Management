package com.angdroid.refrigerator_manament.application

import android.app.Application
import com.angdroid.refrigerator_manament.BuildConfig
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App:Application() {
    lateinit var firebaseUserReference:CollectionReference
    lateinit var firebaseFoodReference:CollectionReference
    lateinit var firebaseRecipeReference:CollectionReference
    override fun onCreate() {
        super.onCreate()
        firebaseUserReference = Firebase.firestore.collection("User")
        firebaseFoodReference = Firebase.firestore.collection("Food")
        firebaseRecipeReference = Firebase.firestore.collection("Recipe")

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}