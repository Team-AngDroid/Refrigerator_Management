package com.angdroid.refrigerator_manament.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeEntity(
    val id: String,
    val name: String,
    val time: String,
    val link: String,
    val image: String,
    val foodList: List<String>
) : Parcelable
