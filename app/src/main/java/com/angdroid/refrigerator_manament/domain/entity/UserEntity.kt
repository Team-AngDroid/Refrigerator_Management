package com.angdroid.refrigerator_manament.domain.entity

data class UserEntity(val id: String, val name: String, val image: String, val foodInfo: List<FoodInfoEntity>)
