package com.angdroid.refrigerator_manament.data.datasource.home

import com.angdroid.refrigerator_manament.data.dto.FoodDto
import com.angdroid.refrigerator_manament.data.dto.FoodInfoDto
import com.angdroid.refrigerator_manament.data.dto.UserDto
import javax.inject.Inject

class UserInfoDataSourceImpl @Inject constructor() : UserInfoDataSource {
    override suspend fun getUserInfo(): UserDto {
        return UserDto("", "", "", listOf(FoodInfoDto(0, FoodDto("", "", "", ""))))
    }
}