package com.angdroid.refrigerator_manament.domain.usecase

/**
 * UserData를 가져올 함수 이후 name:String, Image : String형태가 될 것
 */
interface GetUserProfileUseCase {
    operator fun invoke() : Unit
}