package com.angdroid.refrigerator_manament.domain.usecase

import com.angdroid.refrigerator_manament.domain.repository.FireBaseRepository
import javax.inject.Inject

class GetRecipeListImpl @Inject constructor(private val fireBaseRepository: FireBaseRepository) :
    GetRecipeList {
    override fun invoke(): List<String> {
        TODO("Not yet implemented")
    }
}