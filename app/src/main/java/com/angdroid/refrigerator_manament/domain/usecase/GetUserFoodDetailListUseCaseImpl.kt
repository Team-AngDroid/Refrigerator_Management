package com.angdroid.refrigerator_manament.domain.usecase

import com.angdroid.refrigerator_manament.domain.repository.FireBaseRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserFoodDetailListUseCaseImpl @Inject constructor(private val fireBaseRepository: FireBaseRepository) :
    GetUserFoodDetailListUseCase {
    override fun invoke(ingredient: String) =
        flow { emit(fireBaseRepository.getFood(ingredient)?.sortedBy { it.expirationDate }) }
}