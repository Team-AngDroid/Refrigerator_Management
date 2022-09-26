package com.angdroid.refrigerator_manament.domain.util

sealed class ApiResult<out T> {
    object Init : ApiResult<Nothing>()
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Loading(val isLoading: Boolean) : ApiResult<Nothing>()
    data class Empty(val isEmpty: Boolean) : ApiResult<Nothing>()
    data class Error(val error: String) : ApiResult<Nothing>()
}