package com.angdroid.refrigerator_manament.data.controller

import com.angdroid.refrigerator_manament.domain.entity.model.IngredientType
import javax.inject.Inject

class FoodInfoControllerImpl @Inject constructor() : FoodInfoController {
    override suspend fun addIngredients(ingredients: List<IngredientType>) {
        TODO("Not yet implemented")
    }
}