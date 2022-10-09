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
    abstract fun bindToDeleteFoodCount(deleteFoodCountImpl: DeleteFoodCountUseCaseImpl):DeleteFoodCountUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindEditFoodCount(editFoodCountImpl: EditFoodCountUseCaseImpl):EditFoodCountUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindToGetFoodCategory(getFoodCategoryImpl: GetFoodCategoryUseCaseImpl):GetFoodCategoryUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindToGetRecipeDetail(getRecipeDetailImpl: GetRecipeDetailUseCaseImpl):GetRecipeDetailUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindToGetRecipeList(getRecipeListImpl: GetRecipeListImplUseCase):GetRecipeListUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindToGetUserFoodCategoryList(getUserFoodCategoryListImpl: GetUserFoodCategoryListUseCaseImpl):GetUserFoodCategoryListUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindToUserFoodDetail(getUserFoodDetailImpl: GetUserFoodDetailUseCaseImpl):GetUserFoodDetailUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindToGetUserProfile(getUserProfileImpl: GetUserProfileUseCaseImpl):GetUserProfileUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindToSetUserFoodDetail(setUserFoodDetailImpl: SetUserFoodDetailUseCaseImpl):SetUserFoodDetailUseCase

}