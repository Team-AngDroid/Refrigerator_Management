package com.angdroid.refrigerator_manament.presentation.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angdroid.refrigerator_manament.domain.usecase.EnterRefrigeratorPageUseCase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
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

    private val storageInstance: FirebaseStorage by lazy { Firebase.storage }
    val uri = storageInstance.getReference("/x2pt8UANvml9SldmWItQ/2c5529bb-b61c-3116-8f01-056eeb6f03d1.jpeg").downloadUrl
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