package com.angdroid.refrigerator_manament.domain.usecase

/**
 * 매개변수로 넣어줄 Food와 관련된 레시피 리스트를 반환하는 함수
 * 마이노 식재료 Detail
 */
interface GetRecommendedRecipeListUseCase {
    operator fun invoke() :List<String>
//TODO  operator fun invoke(food:Food) :List<String>
}