package com.angdroid.refrigerator_manament.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeDto(
    val name: String,
    val time: String,
    val link: String,
    val image: String,
    val foodList: List<String>
) : Parcelable
