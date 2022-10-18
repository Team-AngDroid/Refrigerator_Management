package com.angdroid.refrigerator_manament.data.datasource.home

import com.angdroid.refrigerator_manament.application.App
import com.angdroid.refrigerator_manament.data.dto.FoodDto
import com.angdroid.refrigerator_manament.data.dto.FoodInfoDto
import com.angdroid.refrigerator_manament.data.dto.UserDto
import javax.inject.Inject

class UserInfoDataSourceImpl @Inject constructor() : UserInfoDataSource {
    override suspend fun getUserInfo()= App.fireStoreUserReference.get()
}