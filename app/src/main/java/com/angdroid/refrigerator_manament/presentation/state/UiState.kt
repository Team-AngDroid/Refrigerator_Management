package com.startup.meetiing.presentation.state

sealed class UiState<out T> {
    object Init : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Loading(val isLoading: Boolean) : UiState<Nothing>()
    data class Empty(val isEmpty: Boolean) : UiState<Nothing>()
    data class Error(val error: String) : UiState<Nothing>()
}