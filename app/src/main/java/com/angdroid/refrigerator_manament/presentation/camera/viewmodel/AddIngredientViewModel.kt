package com.angdroid.refrigerator_manament.presentation.camera.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angdroid.refrigerator_manament.domain.entity.model.IngredientType
import com.angdroid.refrigerator_manament.domain.usecase.AddUserFoodInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AddIngredientViewModel @Inject constructor(private val addUserFoodImageUseCase: AddUserFoodInfoUseCase) :
    ViewModel() {

    private val _foodList = MutableStateFlow<List<IngredientType.Food>>(emptyList())
    val foodList get() = _foodList.asStateFlow()

    fun setFoodList(cacheDir: File, success:(Boolean)->Unit) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { addUserFoodImageUseCase(foodList.value.filter { it.fid != "0" }) }
            try {
                cacheDir.listFiles()?.map { file -> file.delete() }
            } catch (_: Exception) {
                success(false)
            }
            success(true)
        }
    }

    fun setIntentFoodList(intentList: List<IngredientType.Food>) {
        _foodList.value = intentList
    }

    fun addDialogFood(food: IngredientType.Food) {
        val currentList = _foodList.value.toMutableList()
        currentList.add(1, food)
        _foodList.value = currentList
    }

    fun removeItem(currentItem: IngredientType.Food) {
        val currentList = _foodList.value.toMutableList()
        currentList.remove(currentItem)
        _foodList.value = currentList
    }

    fun minusItemCount(currentItem: IngredientType.Food) {
        val currentList = _foodList.value.toMutableList()
        val position = _foodList.value.indexOf(currentItem)
        if (currentItem.foodCount != 1) {
            currentList[position] =
                IngredientType.Food(
                    currentItem.fid,
                    currentItem.foodId,
                    currentItem.expirationDate,
                    currentItem.name,
                    currentItem.image,
                    currentItem.categoryId,
                    currentItem.foodCount - 1
                )
            _foodList.value = currentList
        }
    }

    fun plusItemCount(currentItem: IngredientType.Food) {
        val currentList = _foodList.value.toMutableList()
        val position = _foodList.value.indexOf(currentItem)
        currentList[position] =
            IngredientType.Food(
                currentItem.fid,
                currentItem.foodId,
                currentItem.expirationDate,
                currentItem.name,
                currentItem.image,
                currentItem.categoryId,
                currentItem.foodCount + 1
            )
        _foodList.value = currentList
    }
}