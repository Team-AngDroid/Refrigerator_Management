package com.angdroid.refrigerator_manament.domain.usecase

import com.angdroid.refrigerator_manament.domain.repository.FireBaseRepository
import javax.inject.Inject

class GetRecommendedRecipeListUseCaseImpl @Inject constructor(private val fireBaseRepository: FireBaseRepository):
    GetRecommendedRecipeListUseCase {
    override fun invoke(): List<String> {
        TODO("Not yet implemented")
    }
}