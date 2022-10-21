package com.angdroid.refrigerator_manament.di

import com.angdroid.refrigerator_manament.data.mapper.home.UserMapper
import com.angdroid.refrigerator_manament.data.mapper.recipe.RecipeMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    @Singleton
    fun provideToMapper(): UserMapper = UserMapper()

    @Provides
    @Singleton
    fun provideToRecipe(): RecipeMapper = RecipeMapper()
}