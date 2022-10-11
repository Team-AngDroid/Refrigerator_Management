package com.angdroid.refrigerator_manament.domain.usecase

/**
 * 유저가 가지고 있는 특정 음식 카테고리의 전체 개수를 가져오는 함수 ex) 사과 -> (1 + 2 + 3 + 4)총 10개
 */
interface GetUserFoodCategoryCountUseCase {
    operator fun invoke() : List<Int>
}