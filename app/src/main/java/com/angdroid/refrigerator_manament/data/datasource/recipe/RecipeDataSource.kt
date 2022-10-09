package com.angdroid.refrigerator_manament.data.datasource.recipe

import com.angdroid.refrigerator_manament.domain.entity.RecipeEntity

interface RecipeDataSource {
    suspend fun getAllRecipe():List<RecipeEntity>
}