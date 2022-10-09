package com.angdroid.refrigerator_manament.domain.usecase

import com.angdroid.refrigerator_manament.domain.repository.FireBaseRepository
import javax.inject.Inject

class DeleteFoodCountUseCaseImpl @Inject constructor(private val fireBaseRepository: FireBaseRepository) :
    DeleteFoodCountUseCase {
    override fun invoke() {
        TODO("Not yet implemented")
    }
}