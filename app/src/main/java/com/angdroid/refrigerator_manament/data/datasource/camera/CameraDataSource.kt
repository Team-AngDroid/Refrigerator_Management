package com.angdroid.refrigerator_manament.data.datasource.camera

interface CameraDataSource {
    suspend fun getDetectImage():List<ByteArray>
}