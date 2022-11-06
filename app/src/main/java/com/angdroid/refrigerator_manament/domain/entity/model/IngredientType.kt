package com.angdroid.refrigerator_manament.domain.entity.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
sealed class IngredientType(
    val id: Int,
    val type: Int,  //Enum Class 으로 해도 되지만, Category 의 변화를 위해서도 쓰임
    val count: Int, //Category : 한 카테고리의 전체 Count, Food : 한 식재료의 각각의 Count
    open val categoryId: Int //이것만 동치관계
) : Parcelable {
    data class Category(
        val categoryName: String,
        val categoryCount: Int,
        override val categoryId: Int
    ) : IngredientType(categoryId, categoryId, categoryCount, categoryId)

    data class Food(
        val fid: String,
        val foodId: Int,
        val expirationDate: LocalDate,
        val name: String,
        val image: String,
        override val categoryId: Int,
        var foodCount: Int
    ) : IngredientType(foodId, 0, foodCount, categoryId)
}
