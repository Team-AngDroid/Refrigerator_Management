package com.angdroid.refrigerator_manament.presentation.camera.viewmodel

import androidx.lifecycle.ViewModel
import com.angdroid.refrigerator_manament.domain.entity.model.IngredientType
import kotlinx.coroutines.flow.MutableStateFlow

class AddIngredientViewModel : ViewModel() {

    private val _foodList = MutableStateFlow<List<IngredientType.Food>>(emptyList())
    val foodList get() = _foodList


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
        //adapter는 position을 기억하고 있다. 그래서 이전 position 가져오게 된당
//        if (currentItem.foodCount == 1)
//            removeItem(currentItem)
        // 디자이너분 말처럼 1일때 마이너스 버튼을 비활성화 시켜야 한다면 1일때 마이너스 시키면 삭제가 아닌
        // 아무런 동작을 해주는게 맞지 않나 싶어서 우선 바꿈
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
        //adapter는 position을 기억하고 있다. 그래서 이전 position 가져오게 된당
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