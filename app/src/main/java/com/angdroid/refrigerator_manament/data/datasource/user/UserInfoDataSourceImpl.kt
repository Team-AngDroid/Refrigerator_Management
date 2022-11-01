package com.angdroid.refrigerator_manament.data.datasource.user

import com.angdroid.refrigerator_manament.application.App
import com.angdroid.refrigerator_manament.data.dto.FoodDto
import javax.inject.Inject

class UserInfoDataSourceImpl @Inject constructor() : UserInfoDataSource {
    override suspend fun getUserInfo() = App.fireStoreUserReference.get()
    override fun setFoodInfo(foodList: List<FoodDto>) {
        App.fireStoreUserReference.set(mapOf("foodInfo" to foodList)).addOnSuccessListener {
        }.addOnFailureListener { e ->
            throw java.lang.Exception(e.message)
        }
    }
}