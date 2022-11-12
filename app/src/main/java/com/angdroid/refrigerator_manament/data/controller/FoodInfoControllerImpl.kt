package com.angdroid.refrigerator_manament.data.controller

import com.angdroid.refrigerator_manament.data.dto.FoodDto
import com.angdroid.refrigerator_manament.domain.entity.model.IngredientType
import com.google.firebase.firestore.DocumentReference
import javax.inject.Inject

class FoodInfoControllerImpl @Inject constructor(private val fireStoreUserReference: DocumentReference) : FoodInfoController {
    override suspend fun addIngredients(ingredients: List<FoodDto>) {
        TODO("Not yet implemented")
    }
}