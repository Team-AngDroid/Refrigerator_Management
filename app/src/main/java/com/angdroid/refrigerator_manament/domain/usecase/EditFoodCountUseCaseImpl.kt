package com.angdroid.refrigerator_manament.domain.usecase

import com.angdroid.refrigerator_manament.domain.repository.FireBaseRepository
import javax.inject.Inject

class GetUserFoodDetailImpl @Inject constructor(private val fireBaseRepository: FireBaseRepository) :
    GetUserFoodDetail {
    override fun invoke() {
        TODO("Not yet implemented")
    }
}