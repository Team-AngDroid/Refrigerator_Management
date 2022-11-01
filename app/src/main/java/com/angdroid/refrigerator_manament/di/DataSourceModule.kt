package com.angdroid.refrigerator_manament.di

import com.angdroid.refrigerator_manament.data.datasource.user.UserInfoDataSource
import com.angdroid.refrigerator_manament.data.datasource.user.UserInfoDataSourceImpl
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
    abstract fun bindToRecipeDataSource(recipeDataSourceImpl: RecipeDataSourceImpl): RecipeDataSource

    @Binds
    @Singleton
    abstract fun bindToUserInfoDataSource(userInfoDataSourceImpl: UserInfoDataSourceImpl): UserInfoDataSource
}