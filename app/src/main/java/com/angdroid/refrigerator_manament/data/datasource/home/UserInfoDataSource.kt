package com.angdroid.refrigerator_manament.data.datasource.home

import com.angdroid.refrigerator_manament.data.dto.UserDto

interface UserInfoDataSource {
    suspend fun getUserInfo():UserDto
}