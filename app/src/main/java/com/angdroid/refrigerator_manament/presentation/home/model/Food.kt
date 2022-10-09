package com.angdroid.refrigerator_manament.presentation.home.model

import java.time.LocalDate

data class Food(
    val id: Int,
    val expirationDate: LocalDate,
    val name: String,
    val image: String,
    val categoryId: Int,
    val foodCount: Int
) : BaseType {
    override fun getType(): Int = 0
    override fun getCount(): Int = foodCount
}
