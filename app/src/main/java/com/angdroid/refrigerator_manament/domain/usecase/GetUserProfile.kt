package com.angdroid.refrigerator_manament.domain.usecase

interface GetUserProfile { //UserData를 가져올 함수 이후 name:String, Image : String형태가 될 것
    operator fun invoke() : Unit
}