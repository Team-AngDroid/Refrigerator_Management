package com.angdroid.refrigerator_manament.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeDto(
    val image: String ="",
    val foodlist: List<String> = listOf(),
    val name: String = "",
    val link: String = "",
    val time: String = ""
) : Parcelable
