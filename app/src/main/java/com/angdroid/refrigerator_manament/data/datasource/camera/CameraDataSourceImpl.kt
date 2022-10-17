package com.angdroid.refrigerator_manament.data.datasource.camera

import javax.inject.Inject

class CameraDataSourceImpl @Inject constructor():CameraDataSource {
    override suspend fun getDetectImage(): List<ByteArray> {
        return listOf(ByteArray(5))
    }
}