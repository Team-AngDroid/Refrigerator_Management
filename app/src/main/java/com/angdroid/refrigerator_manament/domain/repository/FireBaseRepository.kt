package com.angdroid.refrigerator_manament.domain.repository

import com.angdroid.refrigerator_manament.domain.entity.FoodEntity
import com.angdroid.refrigerator_manament.domain.entity.RecipeEntity
import com.angdroid.refrigerator_manament.domain.entity.UserEntity
import com.angdroid.refrigerator_manament.domain.entity.model.IngredientType

interface FireBaseRepository {
    suspend fun deleteFood(foodDto: FoodEntity)
    suspend fun addFood(vararg foodDto: FoodEntity)
    suspend fun getUserInfo(): UserEntity
    suspend fun getAllRecipe(): List<RecipeEntity>
    suspend fun getIngredientRecipe(ingredient: String): List<RecipeEntity>
    suspend fun getSearchRecipe(name: String, onComplete: (List<RecipeEntity>) -> Unit)
    suspend fun getRecipeNameList(onComplete: (List<String>) -> Unit)
    suspend fun getFoodList(): ArrayList<IngredientType>
    suspend fun getFood(ingredient: String):List<IngredientType.Food>
    suspend fun addIngredients(
        ingredients: List<IngredientType>,
        onApiResult: (Boolean) -> Unit
    )
}