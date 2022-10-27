package com.angdroid.refrigerator_manament.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angdroid.refrigerator_manament.domain.entity.RecipeEntity
import com.angdroid.refrigerator_manament.domain.repository.FireBaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class RecipeViewModel @Inject constructor(private val firebaseRepository: FireBaseRepository) :
    ViewModel() {
    private val _randomRecipeList = MutableStateFlow<List<RecipeEntity>>(listOf()) 
    // 랜덤 레시피 두개
    val randomRecipeList get() = _randomRecipeList

    private val _randomIngredientRecipeList = MutableStateFlow<List<RecipeEntity>>(listOf())
    //랜덤 식재료 레시피들
    val randomIngredientRecipeList get() = _randomIngredientRecipeList

    fun getRandomRecipe(ingredient: List<String>) {

        viewModelScope.launch {
            val list = mutableListOf<RecipeEntity>()

            ingredient.forEach {
                firebaseRepository.getIngredientRecipe(it) { randomIngredient ->
                        list.add(randomIngredient[(randomIngredient.indices).random(Random(System.nanoTime()))])
                    _randomRecipeList.value = list // value값 지정도 firebaseRepository안에 있어야함~!!!!!!!
                }
            }
        }
    }

    fun getIngredientRecipe(ingredient: String) {
        viewModelScope.launch {
            firebaseRepository.getIngredientRecipe(ingredient) {
                _randomIngredientRecipeList.value = it
            }
        }
    }



}