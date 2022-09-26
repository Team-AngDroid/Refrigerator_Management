package com.angdroid.refrigerator_manament.data.util

data class BaseResponse<T> (
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: T?
)