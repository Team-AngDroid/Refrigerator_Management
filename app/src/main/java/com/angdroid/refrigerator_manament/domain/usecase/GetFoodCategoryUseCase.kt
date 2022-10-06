package com.angdroid.refrigerator_manament.domain.usecase

interface GetFoodCategoryUseCase { //음식 카테고리(닭,계란,당근,오이)를 가져오는 함수(사용자의 음식리스트에 쓰일 예정)
    operator fun invoke(): List<String>
}