package com.angdroid.refrigerator_manament.domain.usecase

import com.angdroid.refrigerator_manament.domain.entity.model.IngredientType
import kotlinx.coroutines.flow.Flow

interface EnterRefrigeratorPageUseCase {
    suspend operator fun invoke(): Flow<List<IngredientType>>
}