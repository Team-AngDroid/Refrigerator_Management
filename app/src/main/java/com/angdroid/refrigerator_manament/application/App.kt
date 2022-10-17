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
        firestoreCollectionReference = Firebase.firestore.collection("refrigeratorManagement")
        firestoreCollectionReference.run {
            firebaseUserReference = this.document("User")
            firebaseFoodReference = this.document("Food")
            firebaseRecipeReference = this.document("Recipe")
        }
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    companion object {
        lateinit var firestoreCollectionReference: CollectionReference
        lateinit var firebaseUserReference: DocumentReference
        lateinit var firebaseFoodReference: DocumentReference
        lateinit var firebaseRecipeReference: DocumentReference
        private lateinit var instance: App
        fun getInstance(): Context {
            return instance.applicationContext
        }
    }
}