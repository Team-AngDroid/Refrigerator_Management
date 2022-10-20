package com.angdroid.refrigerator_manament.domain.usecase

import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import com.angdroid.refrigerator_manament.domain.entity.model.IngredientType
import com.angdroid.refrigerator_manament.domain.repository.FireBaseRepository
import com.angdroid.refrigerator_manament.util.CategoryType
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class EnterRefrigeratorPageUseCaseImpl @Inject constructor(private val fireBaseRepository: FireBaseRepository) :
    EnterRefrigeratorPageUseCase {
    override suspend fun invoke(
        coroutineScope: CoroutineScope,
        onComplete: suspend (Flow<MutableList<IngredientType>>) -> Unit
    ) {
        fireBaseRepository.getFoodList { entity ->
            coroutineScope.launch(Dispatchers.Default) {
                val returnValue = async {
                    val result = entity.groupingBy { it.id }
                        .aggregate { _, accumulator: IngredientType?, element, first ->
                            if (first) element
                            else {
                                (accumulator as IngredientType.Food).foodCount =
                                    accumulator.count + element.count
                                accumulator
                            }
                        }.values.sortedBy { it.categoryId }.toMutableList()

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
                    result
                }
                onComplete(flow { emit(returnValue.await()) })
            }
        }
    }
}