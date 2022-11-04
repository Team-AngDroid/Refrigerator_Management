package com.angdroid.refrigerator_manament.data.datasource.user

import com.angdroid.refrigerator_manament.application.App
import com.angdroid.refrigerator_manament.data.dto.FoodDto
import com.google.firebase.firestore.DocumentReference
import javax.inject.Inject

class UserInfoDataSourceImpl @Inject constructor(private val fireStoreUserReference: DocumentReference) : UserInfoDataSource {
    override suspend fun getUserInfo() = fireStoreUserReference.get()
    override fun setFoodInfo(foodList: List<FoodDto>) {
        fireStoreUserReference.set(mapOf("foodInfo" to foodList)).addOnSuccessListener {
        }.addOnFailureListener { e ->
            throw java.lang.Exception(e.message)
        }
    }
}