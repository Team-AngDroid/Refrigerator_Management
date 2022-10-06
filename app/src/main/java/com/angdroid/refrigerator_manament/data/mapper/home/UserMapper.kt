package com.angdroid.refrigerator_manament.data.mapper.home

import com.angdroid.refrigerator_manament.data.dto.UserDto
import com.angdroid.refrigerator_manament.data.util.BaseMapper
import com.angdroid.refrigerator_manament.domain.entity.FoodEntity
import com.angdroid.refrigerator_manament.domain.entity.FoodInfoEntity
import com.angdroid.refrigerator_manament.domain.entity.UserEntity
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class UserMapper : BaseMapper<UserDto, UserEntity> {
    override fun map(from: UserDto): UserEntity =
        UserEntity(from.id, from.name, from.image, from.foodInfo.map {
            FoodInfoEntity(
                it.count, FoodEntity(
                    it.food.id,
                    LocalDate.parse(it.food.expirationDate, DateTimeFormatter.ISO_DATE),
                    it.food.name,
                    it.food.image,
                )
            )
        })
}