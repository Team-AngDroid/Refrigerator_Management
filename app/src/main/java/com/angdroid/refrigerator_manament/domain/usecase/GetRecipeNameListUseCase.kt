package com.angdroid.refrigerator_manament.domain.usecase

/**
 * 레피시 이름 정보 리스트를 가져오는 함수
 */
interface GetRecipeNameListUseCase {
    operator fun invoke():List<String>
}