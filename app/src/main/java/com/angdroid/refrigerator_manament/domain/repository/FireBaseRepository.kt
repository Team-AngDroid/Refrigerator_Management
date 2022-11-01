package com.angdroid.refrigerator_manament.domain.repository

import com.angdroid.refrigerator_manament.domain.entity.RecipeEntity
import com.angdroid.refrigerator_manament.domain.entity.model.IngredientType

interface FireBaseRepository {
    suspend fun getAllRecipe(onComplete: (List<RecipeEntity>) -> Unit)
    suspend fun getIngredientRecipe(ingredient: String, onComplete: (List<RecipeEntity>) -> Unit)
    suspend fun getSearchRecipe(name:String, onComplete: (List<RecipeEntity>) -> Unit)
    suspend fun getRecipeNameList(onComplete: (List<String>) -> Unit)
    suspend fun getFoodList(onComplete: (ArrayList<IngredientType>) -> Unit)
    suspend fun getFood(ingredient: String, onComplete: (List<IngredientType.Food>) -> Unit)
    suspend fun addIngredients(
        ingredients: List<IngredientType>,
        onApiResult: (Boolean) -> Unit
    )
}