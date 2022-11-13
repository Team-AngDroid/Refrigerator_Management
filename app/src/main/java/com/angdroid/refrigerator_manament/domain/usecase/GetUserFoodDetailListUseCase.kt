package com.angdroid.refrigerator_manament.domain.usecase

import com.angdroid.refrigerator_manament.domain.entity.model.IngredientType
import kotlinx.coroutines.flow.Flow

/**
 * 음식상세페이저에서 표시할 각각 음식에 대한 [음식의 갯수,이미지,유통기한]을 가져올 함수 입니다.
 * 마이노 식재료 Detail
 */
interface GetUserFoodDetailListUseCase {
    operator fun invoke(ingredient:String): Flow<List<IngredientType.Food>?>
}