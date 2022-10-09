package com.angdroid.refrigerator_manament.data.datasource.camera

class CameraDataSourceImpl:CameraDataSource {
    override suspend fun getDetectImage(): List<ByteArray> {
        return listOf(ByteArray(5))
    }
}