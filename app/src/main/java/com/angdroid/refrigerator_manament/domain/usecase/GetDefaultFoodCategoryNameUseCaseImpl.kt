package com.angdroid.refrigerator_manament.domain.usecase

import com.angdroid.refrigerator_manament.domain.repository.FireBaseRepository
import javax.inject.Inject

class GetDefaultFoodCategoryNameUseCaseImpl @Inject constructor(private val fireBaseRepository: FireBaseRepository) :
    GetDefaultFoodCategoryNameUseCase {
    override fun invoke(): List<String> {
        TODO("Not yet implemented")
    }
}