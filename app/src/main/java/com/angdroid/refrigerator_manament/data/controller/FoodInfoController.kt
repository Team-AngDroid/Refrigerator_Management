package com.angdroid.refrigerator_manament.data.controller

import com.angdroid.refrigerator_manament.data.dto.FoodDto

interface FoodInfoController {
    suspend fun deleteFood(foodDto: FoodDto)
    suspend fun addFood(vararg foodDto: FoodDto)
}