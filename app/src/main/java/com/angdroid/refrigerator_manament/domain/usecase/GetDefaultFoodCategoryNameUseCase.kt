package com.angdroid.refrigerator_manament.domain.usecase

/**
 * 데이터베이스상 정해져있는 Food 카테고리 이름(과일, 채소등)을 가져오는 함수
 */
interface GetDefaultFoodCategoryNameUseCase {
    operator fun invoke() : List<String>
}