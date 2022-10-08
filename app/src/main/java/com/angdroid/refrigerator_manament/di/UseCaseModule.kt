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
    abstract fun bindToDeleteFoodCount(deleteFoodCountImpl: DeleteFoodCountImpl):DeleteFoodCount

    @Binds
    @ViewModelScoped
    abstract fun bindEditFoodCount(editFoodCountImpl: EditFoodCountImpl):EditFoodCount

    @Binds
    @ViewModelScoped
    abstract fun bindToGetFoodCategory(getFoodCategoryImpl: GetFoodCategoryImpl):GetFoodCategory

    @Binds
    @ViewModelScoped
    abstract fun bindToGetRecipeDetail(getRecipeDetailImpl: GetRecipeDetailImpl):GetRecipeDetail

    @Binds
    @ViewModelScoped
    abstract fun bindToGetRecipeList(getRecipeListImpl: GetRecipeListImpl):GetRecipeList

    @Binds
    @ViewModelScoped
    abstract fun bindToGetUserFoodCategoryList(getUserFoodCategoryListImpl: GetUserFoodCategoryListImpl):GetUserFoodCategoryList

    @Binds
    @ViewModelScoped
    abstract fun bindToUserFoodDetail(getUserFoodDetailImpl: GetUserFoodDetailImpl):GetUserFoodDetail

    @Binds
    @ViewModelScoped
    abstract fun bindToGetUserProfile(getUserProfileImpl: GetUserProfileImpl):GetUserProfile

    @Binds
    @ViewModelScoped
    abstract fun bindToSetUserFoodDetail(setUserFoodDetailImpl: SetUserFoodDetailImpl):SetUserFoodDetail


}