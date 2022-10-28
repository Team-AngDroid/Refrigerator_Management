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
import kotlin.random.Random

@HiltViewModel
class RecipeViewModel @Inject constructor(private val firebaseRepository: FireBaseRepository) :
    ViewModel() {
    private val _randomRecipeList = MutableStateFlow<List<RecipeEntity>>(listOf())
    val randomRecipeList get() = _randomRecipeList
    // 랜덤 레시피 두개

    private val _randomIngredientRecipeList = MutableStateFlow<List<RecipeEntity>>(listOf())
    val randomIngredientRecipeList get() = _randomIngredientRecipeList
    //랜덤 식재료 두개의 레시피들

    private val _searchIngredientRecipeList = MutableStateFlow<List<RecipeEntity>>(listOf())
    val searchIngredientList get() = _searchIngredientRecipeList
    //검색 결과 레시피들

    private val _recipeNameList = MutableStateFlow<List<String>>(listOf())
    val recipeNameList  get() = _recipeNameList
    // 레시피 이름 리스트

    fun getRecipeNameList(){
        viewModelScope.launch{
            firebaseRepository.getRecipeNameList {
                _recipeNameList.value = it
                Timber.e(it.toString())
            }
        }
    }

    fun getRandomRecipe(ingredient: List<String>) {
        viewModelScope.launch {
            val list = mutableListOf<RecipeEntity>()

            ingredient.forEach {
                firebaseRepository.getIngredientRecipe(it) { randomRecipe ->
                    list.add(randomRecipe[(randomRecipe.indices).random(Random(System.nanoTime()))])
                    _randomRecipeList.value =
                        list // value값에 지정해주는 시점도 firebaseRepository안에 있어야함~!!!!!!!
                }
            }
        }
    }

    fun getIngredientRecipe(ingredient: List<String>) {
        viewModelScope.launch {
            val list = mutableListOf<RecipeEntity>()

            ingredient.forEach {
                firebaseRepository.getIngredientRecipe(it) { randomIngredientRecipe ->
                    list.add(RecipeEntity("", it, "", "", "", listOf())) //header용 더미데이터
                    list.addAll(randomIngredientRecipe)
                    _randomIngredientRecipeList.value = list
                }
            }

        }
    }

    fun getIngredientSearchRecipe(ingredient: String) {
        viewModelScope.launch {
            val list = mutableSetOf<RecipeEntity>()
            with(firebaseRepository){
                getSearchRecipe(ingredient){
                    list.addAll(it)
                    _searchIngredientRecipeList.value = list.toList()
                }
                getIngredientRecipe(ingredient) {
                    list.addAll(it)
                    _searchIngredientRecipeList.value = list.toList()
                }
            }
        }
    }
}


