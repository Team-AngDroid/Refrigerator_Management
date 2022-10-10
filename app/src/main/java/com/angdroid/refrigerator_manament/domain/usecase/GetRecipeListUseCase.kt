package com.angdroid.refrigerator_manament.domain.usecase

interface GetRecipeListUseCase { // 레피시정보 리스트를 가져오는 함수
    operator fun invoke():List<String>
}