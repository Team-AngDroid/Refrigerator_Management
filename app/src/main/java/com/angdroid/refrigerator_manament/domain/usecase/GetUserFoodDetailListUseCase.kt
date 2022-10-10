package com.angdroid.refrigerator_manament.domain.usecase

interface GetUserFoodDetailListUseCase { //음식상세페이저에서 표시할 각각 음식에 대한 [음식의 갯수,이미지,유통기한]을 가져올 함수 입니다.
    operator fun invoke(): Unit // List<Food>
}