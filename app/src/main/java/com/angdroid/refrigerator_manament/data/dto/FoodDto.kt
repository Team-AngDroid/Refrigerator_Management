package com.angdroid.refrigerator_manament.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FoodDto(
    val id: String,
    val foodId: Int,
    val expirationDate: String,
    val name: String,
    val image: String?,
    val categoryId: Int,
    var foodCount: Int
) : Parcelable
