package com.angdroid.refrigerator_manament.presentation.home.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(val categoryName: String, val categoryCount: Int, private val categoryId: Int) :
    BaseIngredientType, Parcelable {
    override val id: Int
        get() = categoryId
    override val type: Int
        get() = categoryId
    override val count: Int
        get() = categoryCount
    override val category: Int
        get() = categoryId
}