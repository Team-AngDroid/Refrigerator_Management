package com.angdroid.refrigerator_manament.di

import com.angdroid.refrigerator_manament.domain.usecase.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {
    @Binds
    @ViewModelScoped
    abstract fun bindToGetRecipeList(getRecipeListUseCaseImpl: GetRecipeNameListUseCaseImpl): GetRecipeNameListUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindToGetRecommendedRecipeList(getGetRecommendedRecipeListUseCaseImpl: GetRecommendedRecipeListUseCaseImpl): GetRecommendedRecipeListUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindToGetUserFoodDetailList(getUserFoodDetailListUseCaseImpl: GetUserFoodDetailListUseCaseImpl): GetUserFoodDetailListUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindToAddUserFoodInfo(addUserFoodInfoUseCaseImpl: AddUserFoodInfoUseCaseImpl): AddUserFoodInfoUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindToEnterRefrigeratorPage(enterRefrigeratorPageUseCaseImpl: EnterRefrigeratorPageUseCaseImpl): EnterRefrigeratorPageUseCase
}