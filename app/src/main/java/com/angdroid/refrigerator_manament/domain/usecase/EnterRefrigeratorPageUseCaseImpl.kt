package com.angdroid.refrigerator_manament.domain.usecase

import android.util.Log
import com.angdroid.refrigerator_manament.application.App
import com.angdroid.refrigerator_manament.domain.entity.model.IngredientType
import com.angdroid.refrigerator_manament.domain.repository.FireBaseRepository
import com.angdroid.refrigerator_manament.domain.util.CategoryType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class EnterRefrigeratorPageUseCaseImpl @Inject constructor(private val fireBaseRepository: FireBaseRepository) :
    EnterRefrigeratorPageUseCase {
    override suspend fun invoke(): Flow<MutableList<IngredientType>?> = flow {
        val result =
            fireBaseRepository.getFoodList()
                ?.groupBy { (it as IngredientType.Food).foodId }?.values?.map {
                (it[0]).apply {
                    (this as IngredientType.Food).foodCount = it.map { food -> food.count }.sum()
                }
            }?.sortedBy { it.categoryId }?.toMutableList()
        /*.let { 일단 보류
                it.groupingBy { (it as IngredientType.Food).foodId }
                    .aggregate { _, accumulator: IngredientType?, element, first ->
                        if (first) element
                        else {
                            (accumulator as IngredientType.Food).apply {
                                this.foodCount =
                                    accumulator.count + element.count
                            }
                        }
                    }.values.sortedBy { it.categoryId }.toMutableList()
            }*/
        if (result != null) {
            App.setUserInfo((result as MutableList<IngredientType.Food>))

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
        }
        emit(result)
    }.flowOn(Dispatchers.Default)
}