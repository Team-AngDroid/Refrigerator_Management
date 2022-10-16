package com.angdroid.refrigerator_manament.domain.util

sealed class ApiResult<out T> {
    data class Fail(val message: String) : ApiResult<Nothing>()
    data class Success<T>(val data: T) : ApiResult<T>()
}