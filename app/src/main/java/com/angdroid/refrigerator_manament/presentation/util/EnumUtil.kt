package com.angdroid.refrigerator_manament.presentation.util

import com.angdroid.refrigerator_manament.presentation.util.types.FoodIdType

inline fun <reified T : Enum<FoodIdType>> safeValueOf(type: String?): FoodIdType? {
    if (type == null) return null
    return try {
        java.lang.Enum.valueOf(FoodIdType::class.java, type)
    } catch (e: IllegalArgumentException) {
        null
    }
}