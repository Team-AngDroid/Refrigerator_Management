package com.angdroid.refrigerator_manament.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angdroid.refrigerator_manament.domain.usecase.EnterRefrigeratorPageUseCase
import com.startup.meetiing.presentation.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IngredientViewModel @Inject constructor(
    private val enterRefrigeratorPageUseCase: EnterRefrigeratorPageUseCase
) : ViewModel() {
    private val _ingredient = MutableStateFlow<UiState>(UiState.Init)
    val ingredient get() = _ingredient.asStateFlow()
    init {
        viewModelScope.launch {
            enterRefrigeratorPageUseCase().onStart {
                _ingredient.value = UiState.Loading(true)
            }.catch {
                _ingredient.value = UiState.Error(this.toString())
            }.collect { collect ->
                if (collect.isEmpty()) {
                    _ingredient.value = UiState.Empty(true)
                } else {
                    _ingredient.value = UiState.Success(collect)
                }
            }
        }
    }
}