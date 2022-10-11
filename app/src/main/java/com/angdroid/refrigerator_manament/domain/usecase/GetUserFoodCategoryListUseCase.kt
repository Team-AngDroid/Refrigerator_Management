package com.angdroid.refrigerator_manament.domain.usecase

/**
 * 사용자가 가지고 있는 푸드 카테고리 리스트를 가져옵니다.
 */
interface GetUserFoodCategoryListUseCase {
    operator fun invoke() : List<String>
}