package com.angdroid.refrigerator_manament.domain.usecase

import com.angdroid.refrigerator_manament.domain.repository.FireBaseRepository
import javax.inject.Inject

class EditFoodCountUseCaseImpl @Inject constructor(private val fireBaseRepository: FireBaseRepository) :
    EditFoodCountUseCase {
    override fun invoke() {
        TODO("Not yet implemented")
    }
}