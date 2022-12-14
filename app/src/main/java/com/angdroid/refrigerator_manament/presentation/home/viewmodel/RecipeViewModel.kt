package com.angdroid.refrigerator_manament.presentation.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angdroid.refrigerator_manament.domain.entity.RecipeEntity
import com.angdroid.refrigerator_manament.domain.repository.FireBaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class RecipeViewModel @Inject constructor(private val firebaseRepository: FireBaseRepository) :
    ViewModel() {
    private val _randomRecipeList = MutableStateFlow<List<RecipeEntity>>(listOf())
    val randomRecipeList get() = _randomRecipeList.asStateFlow()
    // 랜덤 레시피 두개

    private val _randomIngredientRecipeList = MutableStateFlow<List<RecipeEntity>>(listOf())
    val randomIngredientRecipeList get() = _randomIngredientRecipeList.asStateFlow()
    //랜덤 식재료 두개의 레시피들

    private val _searchIngredientRecipeList = MutableStateFlow<List<RecipeEntity>>(listOf())
    val searchIngredientList get() = _searchIngredientRecipeList.asStateFlow()
    //검색 결과 레시피들

    private val _recipeNameList = MutableStateFlow<List<String>>(listOf())
    val recipeNameList get() = _recipeNameList.asStateFlow()
    // 레시피 이름 리스트

    fun getRecipeNameList() {
        viewModelScope.launch {
            firebaseRepository.getRecipeNameList().let {
                _recipeNameList.value = it
            }
        }
    }

    fun getRandomRecipe(ingredient: List<String>) {
        viewModelScope.launch {
            val list = mutableListOf<RecipeEntity>()
            ingredient.forEach {
                firebaseRepository.getIngredientRecipe(it).let { randomRecipe ->
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
                firebaseRepository.getIngredientRecipe(it).let { randomIngredientRecipe ->
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
            with(firebaseRepository) {
                getSearchRecipe(ingredient).let { // [레시피이름]에 검색창에서 입력받은 텍스트가 있는 경우
                    list.addAll(it)
                }
                getIngredientRecipe(ingredient).let { // [레시피 재료 리스트]에 검색창에서 입력받은 텍스트가 있는 경우
                    list.addAll(it)
                    _searchIngredientRecipeList.value =
                        list.distinctBy { it.name } // RecipeEntity 중복 제거
                }
            }
        }
    }

    /**
     * 프래그먼트 전환간 리스트 비워주는 함수
     */
    fun clearSearchRecipe() {
        _searchIngredientRecipeList.value = listOf()
    }
}


