package com.angdroid.refrigerator_manament.domain.usecase

import com.angdroid.refrigerator_manament.domain.repository.FireBaseRepository
import javax.inject.Inject


//Todo("User Profile 보여줄지 고민)
class GetUserProfileImpl @Inject constructor(private val fireBaseRepository: FireBaseRepository) :
    GetUserProfile {
    override fun invoke() {
        TODO("Not yet implemented")
    }
}