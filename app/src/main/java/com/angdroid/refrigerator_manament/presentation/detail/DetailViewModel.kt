package com.angdroid.refrigerator_manament.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angdroid.refrigerator_manament.domain.entity.RecipeEntity
import com.angdroid.refrigerator_manament.domain.repository.FireBaseRepository
import com.angdroid.refrigerator_manament.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val firebaseRepository: FireBaseRepository) :
    ViewModel() {
    private val _recipeList = MutableStateFlow<List<RecipeEntity>>(listOf())
    val recipeList get() = _recipeList
    fun getAllRecipe() {
        viewModelScope.launch {

            firebaseRepository.getAllRecipe {
                _recipeList.value = it
            }
        }
    }
}