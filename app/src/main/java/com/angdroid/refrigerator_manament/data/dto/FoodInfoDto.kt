package com.angdroid.refrigerator_manament.data.dto

data class FoodInfoDto(val count:Int, val food: FoodDto){
    data class FoodDto(val id: String, val expirationDate: String, val name: String, val image: String)
}
