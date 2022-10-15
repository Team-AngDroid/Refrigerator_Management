package com.angdroid.refrigerator_manament.presentation.home.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class Food(
    val fid: String,
    val foodId: Int,
    val expirationDate: LocalDate,
    val name: String,
    val image: String,
    val categoryId: Int,
    var foodCount: Int
) : Parcelable, BaseType {
    override val id: Int
        get() = foodId
    override val type: Int
        get() = 0
    override val count: Int
        get() = foodCount
    override val category: Int
        get() = categoryId
}