package com.angdroid.refrigerator_manament.di

import com.angdroid.refrigerator_manament.BuildConfig
import com.angdroid.refrigerator_manament.data.controller.FoodInfoController
import com.angdroid.refrigerator_manament.data.controller.FoodInfoControllerImpl
import com.angdroid.refrigerator_manament.data.controller.StorageController
import com.angdroid.refrigerator_manament.data.controller.StorageControllerImpl
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ControllerModule {

    private val fireStoreUserReference
        get() = Firebase.firestore.collection("User").document(BuildConfig.USER_ID)
    private val firebaseStorage: StorageReference
        get() = Firebase.storage.getReference(BuildConfig.USER_ID)

    @Provides
    @Singleton
    fun provideToStorageController(): StorageController = StorageControllerImpl(firebaseStorage)

    @Provides
    @Singleton
    fun provideToFoodInfoController(): FoodInfoController = FoodInfoControllerImpl(fireStoreUserReference)
}