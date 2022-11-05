package com.angdroid.refrigerator_manament.domain.repository

import com.angdroid.refrigerator_manament.domain.entity.RecipeEntity
import com.angdroid.refrigerator_manament.domain.entity.model.IngredientType

interface FireBaseRepository {
    suspend fun getAllRecipe(): List<RecipeEntity>
    suspend fun getIngredientRecipe(ingredient: String): List<RecipeEntity>
    suspend fun getSearchRecipe(name: String): List<RecipeEntity>
    suspend fun getRecipeNameList(): List<String>
    suspend fun getFoodList(): ArrayList<IngredientType>
    suspend fun getFood(ingredient: String): List<IngredientType.Food>
    suspend fun addIngredients(
        ingredients: List<IngredientType>,
        onApiResult: (Boolean) -> Unit
    )
    suspend fun upLoadFoodImage(paths: List<String>, byteArrayImages: List<ByteArray>)
}