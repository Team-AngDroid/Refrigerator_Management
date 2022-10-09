package com.angdroid.refrigerator_manament.data.datasource.recipe

import com.angdroid.refrigerator_manament.domain.entity.RecipeEntity

class RecipeDataSourceImpl:RecipeDataSource {
    override suspend fun getAllRecipe(): List<RecipeEntity> {
        return listOf(RecipeEntity("","", listOf("")))
    }
}