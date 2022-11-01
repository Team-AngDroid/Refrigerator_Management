package com.angdroid.refrigerator_manament.domain.usecase

import com.angdroid.refrigerator_manament.application.App
import com.angdroid.refrigerator_manament.domain.entity.model.IngredientType
import com.angdroid.refrigerator_manament.domain.repository.FireBaseRepository
import com.angdroid.refrigerator_manament.util.CategoryType
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EnterRefrigeratorPageUseCaseImpl @Inject constructor(private val fireBaseRepository: FireBaseRepository) :
    EnterRefrigeratorPageUseCase {
    override suspend fun invoke(): Flow<MutableList<IngredientType>> = flow {
        val result = fireBaseRepository.getFoodList().groupingBy { it.id }
            .aggregate { _, accumulator: IngredientType?, element, first ->
                if (first) element
                else {
                    (accumulator as IngredientType.Food).foodCount =
                        accumulator.count + element.count
                    accumulator
                }
            }.values.sortedBy { it.categoryId }.toMutableList()
        App.userIngredientInfo = result.map { (it as IngredientType.Food).name }
        var categoryTemp: Pair<Int, Int> =
            Pair(result[result.size - 1].categoryId, result[result.size - 1].count)

        for (i in result.size - 1 downTo 0) {
            result[i].run {
                if (this.categoryId != categoryTemp.first) {
                    result.add(
                        i + 1,
                        IngredientType.Category(
                            CategoryType.categoryStringList[categoryTemp.first - 1],
                            categoryTemp.second,
                            categoryTemp.first
                        )
                    )
                    categoryTemp = Pair(this.categoryId, this.count)
                } else {
                    categoryTemp.second.plus(this.count)
                }
            }
        }
        result.add(
            0,
            IngredientType.Category(
                CategoryType.categoryStringList[categoryTemp.first - 1],
                categoryTemp.second,
                categoryTemp.first
            )
        )
        emit(result)
    }
}