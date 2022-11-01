package com.angdroid.refrigerator_manament.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angdroid.refrigerator_manament.domain.entity.RecipeEntity
import com.angdroid.refrigerator_manament.domain.entity.model.IngredientType
import com.angdroid.refrigerator_manament.domain.repository.FireBaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val firebaseRepository: FireBaseRepository) :
    ViewModel() {
    private val _recipeList = MutableStateFlow<List<RecipeEntity>>(listOf())
    private val _foodList = MutableStateFlow<List<IngredientType.Food>>(listOf())
    val recipeList get() = _recipeList
    val foodList get() = _foodList
    val selectItem = MutableStateFlow<IngredientType.Food?>(null)
    fun getAllRecipe() {
        viewModelScope.launch {

            firebaseRepository.getAllRecipe {
                _recipeList.value = it
            }
        }
    }

    fun getIngredientRecipe(ingredient: String) {

        viewModelScope.launch {
            firebaseRepository.getIngredientRecipe(ingredient) {
                _recipeList.value = it
            }
            firebaseRepository.getFood(ingredient) {
                _foodList.value = it.sortedBy { it.expirationDate }
            }
        }
    }
}