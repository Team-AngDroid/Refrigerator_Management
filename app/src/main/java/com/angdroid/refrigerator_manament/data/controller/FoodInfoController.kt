package com.angdroid.refrigerator_manament.data.controller

import com.angdroid.refrigerator_manament.data.dto.FoodDto
import com.angdroid.refrigerator_manament.domain.entity.model.IngredientType

interface FoodInfoController {
    suspend fun addIngredients(
        ingredients: List<IngredientType>
    )
}