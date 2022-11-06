package com.angdroid.refrigerator_manament.data.mapper.user

import com.angdroid.refrigerator_manament.data.dto.FoodDto
import com.angdroid.refrigerator_manament.data.util.BaseMapper
import com.angdroid.refrigerator_manament.domain.entity.model.IngredientType
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class UserMapper : BaseMapper<List<FoodDto>, List<IngredientType.Food>> {
    override fun mapToEntity(from: List<FoodDto>): List<IngredientType.Food> =
        from.map { dto ->
            IngredientType.Food(
                dto.id,
                dto.foodId,
                LocalDate.parse(dto.expirationDate?:LocalDate.now().toString(), DateTimeFormatter.ISO_DATE),
                dto.name,
                null,
                dto.categoryId,
                dto.foodCount
            )
        }


    override fun mapToDto(from: List<IngredientType.Food>): List<FoodDto> =
        from.map { dto ->
            FoodDto(
                dto.fid,
                dto.foodId,
                dto.expirationDate.toString(),
                dto.name,
                dto.image.toString(),
                dto.categoryId,
                dto.foodCount
            )
        }
}