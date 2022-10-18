package com.angdroid.refrigerator_manament.data.mapper.home

import com.angdroid.refrigerator_manament.data.dto.FoodDto
import com.angdroid.refrigerator_manament.data.dto.UserDto
import com.angdroid.refrigerator_manament.data.util.BaseMapper
import com.angdroid.refrigerator_manament.domain.entity.FoodEntity
import com.angdroid.refrigerator_manament.domain.entity.FoodInfoEntity
import com.angdroid.refrigerator_manament.domain.entity.UserEntity
import com.angdroid.refrigerator_manament.domain.entity.model.IngredientType
import com.google.firebase.Timestamp
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class UserMapper : BaseMapper<List<FoodDto>, List<IngredientType.Food>> {
    override fun mapToEntity(from: List<FoodDto>): List<IngredientType.Food> =
        from.map { dto ->
            IngredientType.Food(
                dto.id,
                dto.foodId,
                LocalDate.parse(dto.expirationDate, DateTimeFormatter.ISO_DATE),
                dto.name,
                dto.image,
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
                dto.image,
                dto.categoryId,
                dto.foodCount
            )
        }
}