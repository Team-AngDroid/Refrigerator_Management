package com.angdroid.refrigerator_manament.di

import com.angdroid.refrigerator_manament.BuildConfig
import com.angdroid.refrigerator_manament.data.datasource.user.UserInfoDataSource
import com.angdroid.refrigerator_manament.data.datasource.user.UserInfoDataSourceImpl
import com.angdroid.refrigerator_manament.data.datasource.recipe.RecipeDataSource
import com.angdroid.refrigerator_manament.data.datasource.recipe.RecipeDataSourceImpl
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataSourceModule {

    @Provides
    @Singleton
    fun provideFireStoreRecipeReference(): CollectionReference =
        Firebase.firestore.collection("Recipe")

    @Provides
    @Singleton
    fun provideFireStoreUserReference(): DocumentReference =
        Firebase.firestore.collection("User").document(BuildConfig.USER_ID)

    @Provides
    @Singleton
    fun provideToRecipeDataSource(fireStoreRecipeReference: CollectionReference): RecipeDataSource =
        RecipeDataSourceImpl(fireStoreRecipeReference)

    @Provides
    @Singleton
    fun provideToUserInfoDataSource(fireStoreUserReference: DocumentReference): UserInfoDataSource =
        UserInfoDataSourceImpl(fireStoreUserReference)
}