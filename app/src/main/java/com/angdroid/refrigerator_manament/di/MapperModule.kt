package com.angdroid.refrigerator_manament.di

import com.angdroid.refrigerator_manament.data.mapper.home.UserMapper
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
    fun bindToMapper(): UserMapper = UserMapper()
}