package com.angdroid.refrigerator_manament.domain.usecase

import androidx.lifecycle.LifecycleCoroutineScope
import com.angdroid.refrigerator_manament.domain.entity.model.IngredientType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface EnterRefrigeratorPageUseCase {
    suspend operator fun invoke(coroutineScope: CoroutineScope, onComplete: suspend (Flow<MutableList<IngredientType>>) -> Unit)
}