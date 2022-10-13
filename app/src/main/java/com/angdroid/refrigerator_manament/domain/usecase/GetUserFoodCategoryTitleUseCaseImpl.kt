package com.angdroid.refrigerator_manament.domain.usecase

import com.angdroid.refrigerator_manament.domain.repository.FireBaseRepository
import javax.inject.Inject

class GetUserFoodCategoryTitleUseCaseImpl @Inject constructor(private val fireBaseRepository: FireBaseRepository) :
    GetUserFoodCategoryTitleUseCase {
    override fun invoke(): List<String> {
        TODO("Not yet implemented")
    }
}