package com.angdroid.refrigerator_manament.domain.usecase

/**
 * 유저가 가지고 있는 음식중 음식 카테고리이름(닭,계란,당근,오이)를 가져오는 함수(사용자의 음식리스트에 쓰일 예정)
 */
interface GetUserFoodCategoryTitleUseCase {
    operator fun invoke(): List<String>
}