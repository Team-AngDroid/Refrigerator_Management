package com.angdroid.refrigerator_manament.presentation.home.model

import com.angdroid.refrigerator_manament.data.dto.CategoryType
import java.time.LocalDate

data class Food(val id: String, val expirationDate: LocalDate, val name: String, val image: String)
