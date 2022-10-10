package com.angdroid.refrigerator_manament.domain.usecase

interface GetDefaultFoodDetailUseCase { //데이터베이스상 대표 음식 (ex: 사과,당근,생닭) 이름과 이미지를 가져오는 함수
    operator fun invoke(): List<String> //Food
}