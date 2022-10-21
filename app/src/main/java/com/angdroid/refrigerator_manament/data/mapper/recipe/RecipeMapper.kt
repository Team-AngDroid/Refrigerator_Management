package com.angdroid.refrigerator_manament.data.mapper.recipe

import com.angdroid.refrigerator_manament.data.dto.RecipeDto
import com.angdroid.refrigerator_manament.data.util.BaseMapper
import com.angdroid.refrigerator_manament.domain.entity.RecipeEntity

class RecipeMapper : BaseMapper<List<RecipeDto>, List<RecipeEntity>> {
    override fun mapToEntity(from: List<RecipeDto>): List<RecipeEntity> {
        var count = 0
        return from.map { item ->
            RecipeEntity(
                count++.toString(),
                item.name,
                item.time,
                item.link,
                item.image,
                item.foodlist
            )
        }
    }

    override fun mapToDto(from: List<RecipeEntity>): List<RecipeDto> {
        TODO()
    }
}