package com.angdroid.refrigerator_manament.presentation.camera.viewmodel

import androidx.lifecycle.ViewModel
import com.angdroid.refrigerator_manament.domain.entity.model.IngredientType
import kotlinx.coroutines.flow.MutableStateFlow

class CameraViewModel : ViewModel() {

    private val _foodList = MutableStateFlow<List<IngredientType.Food>>(emptyList())
    val foodList get() = _foodList


    fun setIntentFoodList(intentList: List<IngredientType.Food>) {
        _foodList.value = intentList
    }

    fun removeItem(currentItem: IngredientType.Food) {
        val currentList = _foodList.value.toMutableList()
        currentList.remove(currentItem)
        _foodList.value = currentList
    }

    fun minusItemCount(currentItem: IngredientType.Food) {
        val currentList = _foodList.value.toMutableList()
        val position = _foodList.value.indexOf(currentItem)
        //adapter는 position을 기억하고 있다. 그래서 이전 position 가져오게 된당
        if (currentItem.foodCount == 1)
            removeItem(currentItem)
        else {
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
}