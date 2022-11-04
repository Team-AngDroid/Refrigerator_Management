package com.angdroid.refrigerator_manament.domain.usecase

import com.angdroid.refrigerator_manament.domain.entity.RecipeEntity
import com.angdroid.refrigerator_manament.domain.entity.model.IngredientType
import com.angdroid.refrigerator_manament.domain.repository.FireBaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRecommendedRecipeListUseCaseImpl @Inject constructor(private val fireBaseRepository: FireBaseRepository) :
    GetRecommendedRecipeListUseCase {
    override fun invoke(ingredient: String): Flow<List<RecipeEntity>> = flow {
        emit(fireBaseRepository.getIngredientRecipe(ingredient))
    }
}