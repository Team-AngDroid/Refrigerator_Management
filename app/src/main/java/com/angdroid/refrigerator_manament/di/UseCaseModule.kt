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
    abstract fun bindToDeleteUserFoodCount(deleteUserFoodCountUseCaseImpl: DeleteUserFoodCountUseCaseImpl):DeleteUserFoodCountUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindEditUserFoodCount(editUserFoodCountUseCaseImpl: EditUserFoodCountUseCaseImpl):EditUserFoodCountUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindToGetRecipeDetail(getRecipeDetailUseCaseImpl: GetRecipeDetailUseCaseImpl):GetRecipeDetailUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindToGetRecipeList(getRecipeListUseCaseImpl: GetRecipeListUseCaseImpl):GetRecipeListUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindToGetRecommendedRecipeList(getGetRecommendedRecipeListUseCaseImpl: GetRecommendedRecipeListUseCaseImpl):GetRecommendedRecipeListUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindToGetDefaultFoodDetail(getDefaultFoodDetailUseCaseImpl: GetDefaultFoodDetailUseCaseImpl):GetDefaultFoodDetailUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindToGetDefaultFoodCategoryName(getDefaultFoodCategoryNameUseCaseImpl: GetDefaultFoodCategoryNameUseCaseImpl):GetDefaultFoodCategoryNameUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindToGetUserFoodCategoryTitle(getUserFoodCategoryTitleUseCaseImpl: GetUserFoodCategoryTitleUseCaseImpl):GetUserFoodCategoryTitleUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindToGetUserFoodCategoryCount(getUserFoodCategoryCountUseCaseImpl: GetUserFoodCategoryCountUseCaseImpl):GetUserFoodCategoryCountUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindToGetUserFoodCategoryList(getUserFoodCategoryListUseCaseImpl: GetUserFoodCategoryListUseCaseImpl):GetUserFoodCategoryListUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindToGetUserFoodDetailList(getUserFoodDetailListUseCaseImpl: GetUserFoodDetailListUseCaseImpl):GetUserFoodDetailListUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindToGetUserProfile(getUserProfileUseCaseImpl: GetUserProfileUseCaseImpl):GetUserProfileUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindToSetUserFoodDetail(setUserFoodDetailUseCaseImpl: SetUserFoodDetailUseCaseImpl):SetUserFoodDetailUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindToEnterRefrigeratorPage(enterRefrigeratorPageUseCaseImpl: EnterRefrigeratorPageUseCaseImpl):EnterRefrigeratorPageUseCase

}