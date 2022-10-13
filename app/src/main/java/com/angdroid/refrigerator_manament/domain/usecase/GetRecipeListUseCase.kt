package com.angdroid.refrigerator_manament.domain.usecase

/**
 * 레피시정보 리스트를 가져오는 함수
 */
interface GetRecipeListUseCase {
    operator fun invoke():List<String>
}