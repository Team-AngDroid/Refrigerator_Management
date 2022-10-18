package com.angdroid.refrigerator_manament.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angdroid.refrigerator_manament.domain.usecase.EnterRefrigeratorPageUseCase
import com.angdroid.refrigerator_manament.domain.entity.model.IngredientType
import com.startup.meetiing.presentation.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IngredientViewModel @Inject constructor(
    private val enterRefrigeratorPageUseCase: EnterRefrigeratorPageUseCase
) : ViewModel() {
    private val _ingredient = MutableStateFlow<UiState<List<IngredientType>>>(UiState.Init)
    val ingredient get() = _ingredient

    init {
        viewModelScope.launch {
            enterRefrigeratorPageUseCase(viewModelScope) {
                it.onStart {
                    _ingredient.value = UiState.Loading(true)
                    Log.e("onStart???", "WHY?!?!?!?")
                }.catch {
                    Log.e("onCatch???", "WHY?!?!?!?")
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

}