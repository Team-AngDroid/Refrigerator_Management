package com.angdroid.refrigerator_manament.data.mapper.recipe

import com.angdroid.refrigerator_manament.data.dto.RecipeDto
import com.angdroid.refrigerator_manament.data.util.BaseMapper
import com.angdroid.refrigerator_manament.domain.entity.RecipeEntity

class RecipeMapper : BaseMapper<RecipeDto, RecipeEntity> {
    override fun mapToEntity(from: RecipeDto): RecipeEntity {
        TODO()
    }

    override fun mapToDto(from: RecipeEntity): RecipeDto {
        TODO()
    }
}