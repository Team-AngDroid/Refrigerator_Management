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
    suspend fun getFoodList(): ArrayList<IngredientType>
}