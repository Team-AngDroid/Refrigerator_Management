package com.angdroid.refrigerator_manament.domain.entity

import java.time.LocalDate

data class FoodEntity(val id: String, val expirationDate: LocalDate, val name: String, val image: String)
