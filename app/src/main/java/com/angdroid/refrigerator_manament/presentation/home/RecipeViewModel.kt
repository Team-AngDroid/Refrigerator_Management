package com.angdroid.refrigerator_manament.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angdroid.refrigerator_manament.domain.entity.RecipeEntity
import com.angdroid.refrigerator_manament.domain.repository.FireBaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(private val firebaseRepository: FireBaseRepository) :
    ViewModel() {
    private val _randomRecipeList = MutableStateFlow<List<RecipeEntity>>(listOf()) 
    // 랜덤 레시피 두개
    val randomRecipeList get() = _randomRecipeList

    private val _randomIngredientRecipeList = MutableStateFlow<List<RecipeEntity>>(listOf())
    //랜덤 식재료 레시피들
    val randomIngredientRecipeList get() = _randomIngredientRecipeList

    fun getIngredientRecipe(ingredient: List<String>) {

        viewModelScope.launch {
            val list = mutableListOf<RecipeEntity>()

            ingredient.forEach {
                firebaseRepository.getIngredientRecipe(it) { randomIngredient ->
                        list.add(randomIngredient[(0..randomIngredient.size).random()])
                    Timber.e(list.toString())
                    _randomIngredientRecipeList.value = list // value값 지정도 firebaseRepository안에 있어야함~!!!!!!!
                    Timber.e(_randomIngredientRecipeList.value.toString())
                }
            }
        }
    }

    fun getRandomRecipe(ingredient: String) {

        viewModelScope.launch {
            firebaseRepository.getIngredientRecipe(ingredient) {
                _randomRecipeList.value = it
            }
        }
    }



}