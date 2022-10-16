package com.angdroid.refrigerator_manament.domain.usecase

import com.angdroid.refrigerator_manament.domain.repository.FireBaseRepository
import com.angdroid.refrigerator_manament.presentation.home.model.IngredientType
import com.angdroid.refrigerator_manament.presentation.util.types.CategoryType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class EnterRefrigeratorPageUseCaseImpl @Inject constructor(private val fireBaseRepository: FireBaseRepository) : EnterRefrigeratorPageUseCase {
    override suspend fun invoke(): Flow<List<IngredientType>> = flow {
        val result = fireBaseRepository.getFoodList().groupingBy { it.id }
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
        emit(result)
    }.flowOn(Dispatchers.Default)  // TODO 나중에 IO로 변경할지 고민, 데이터 받아오는건 IO가 맞는데 이 연산은 default가 맞아보임
}