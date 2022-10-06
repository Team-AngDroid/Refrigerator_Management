package com.angdroid.refrigerator_manament.data.dto

data class FoodInfo(val count:Int, val food: Food){
    data class Food(val id: String, val expirationDate: String, val name: String, val image: String)
}
