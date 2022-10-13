package com.angdroid.refrigerator_manament.domain.usecase

/**
 * 유저가 사진을 찍은 food에 대한 count와 날짜를 서버에 set
 */
interface SetUserFoodDetailUseCase {
    operator fun invoke(): Unit
}