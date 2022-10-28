package com.angdroid.refrigerator_manament.data.datasource.home

import com.angdroid.refrigerator_manament.data.dto.FoodDto
import com.angdroid.refrigerator_manament.domain.entity.model.IngredientType
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot

interface UserInfoDataSource {
    suspend fun getUserInfo(): Task<DocumentSnapshot>
    fun setFoodInfo(foodList: List<FoodDto>)
}