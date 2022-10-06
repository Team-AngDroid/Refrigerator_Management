package com.angdroid.refrigerator_manament.domain.usecase

interface GetUserFoodDetail { //음식리스트에서 음식의 갯수, 유통기한을 가져올 함수 입니다.
    operator fun invoke(): Unit
}