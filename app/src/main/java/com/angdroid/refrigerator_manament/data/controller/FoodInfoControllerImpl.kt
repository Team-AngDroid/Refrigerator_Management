package com.angdroid.refrigerator_manament.data.controller

import com.angdroid.refrigerator_manament.data.dto.FoodDto
import javax.inject.Inject

class FoodInfoControllerImpl @Inject constructor() : FoodInfoController {
    override suspend fun deleteFood(foodDto: FoodDto) {
        TODO("Not yet implemented")
    }

    override suspend fun addFood(vararg foodDto: FoodDto) {
        TODO("Not yet implemented")
    }
}