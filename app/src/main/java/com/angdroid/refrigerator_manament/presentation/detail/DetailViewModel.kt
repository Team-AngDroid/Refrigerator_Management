package com.angdroid.refrigerator_manament.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angdroid.refrigerator_manament.domain.entity.model.IngredientType
import com.angdroid.refrigerator_manament.domain.usecase.GetRecommendedRecipeListUseCase
import com.angdroid.refrigerator_manament.domain.usecase.GetUserFoodDetailListUseCase
import com.startup.meetiing.presentation.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getUserFoodDetailListUseCase: GetUserFoodDetailListUseCase,
    private val getRecommendedRecipeListUseCase: GetRecommendedRecipeListUseCase
) :
    ViewModel() {
    private val _recipeList = MutableStateFlow<UiState>(UiState.Init)
    private val _foodList = MutableStateFlow<UiState>(UiState.Init)
    val recipeList get() = _recipeList
    val foodList get() = _foodList
    val selectItem = MutableStateFlow<IngredientType.Food?>(null)
    fun getIngredientRecipe(ingredient: String) {

        viewModelScope.launch {
            getRecommendedRecipeListUseCase(ingredient).onStart {
            }.catch {
            }.collect {
                _recipeList.value = UiState.Success(it)
            }
            getUserFoodDetailListUseCase(ingredient).onStart {
            }.catch {
            }.collect {
                if (it != null) {
                    _foodList.value = UiState.Success(it)
                } else {
                    _foodList.value = UiState.Empty(true)
                }
            }
        }
    }
}