package com.angdroid.refrigerator_manament.di

import com.angdroid.refrigerator_manament.data.datasource.camera.CameraDataSource
import com.angdroid.refrigerator_manament.data.datasource.camera.CameraDataSourceImpl
import com.angdroid.refrigerator_manament.data.datasource.home.UserInfoDataSource
import com.angdroid.refrigerator_manament.data.datasource.home.UserInfoDataSourceImpl
import com.angdroid.refrigerator_manament.data.datasource.recipe.RecipeDataSource
import com.angdroid.refrigerator_manament.data.datasource.recipe.RecipeDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindToCameraDataSource(cameraDataSourceImpl: CameraDataSourceImpl):CameraDataSource

    @Binds
    @Singleton
    abstract fun bindToRecipeDataSource(recipeDataSourceImpl: RecipeDataSourceImpl):RecipeDataSource

    @Binds
    @Singleton
    abstract fun bindToUserInfoDataSource(userInfoDataSourceImpl: UserInfoDataSourceImpl):UserInfoDataSource
}