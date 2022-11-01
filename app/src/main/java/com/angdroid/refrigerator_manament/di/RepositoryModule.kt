package com.angdroid.refrigerator_manament.di

import com.angdroid.refrigerator_manament.data.repository.FireBaseRepositoryImpl
import com.angdroid.refrigerator_manament.domain.repository.FireBaseRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindToFireBaseRepository(fireBaseRepositoryImpl: FireBaseRepositoryImpl):FireBaseRepository
}
