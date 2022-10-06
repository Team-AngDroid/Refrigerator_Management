package com.angdroid.refrigerator_manament.data.repository.camera

import com.angdroid.refrigerator_manament.data.datasource.camera.CameraDataSource
import com.angdroid.refrigerator_manament.domain.repository.CameraRepository

class CameraRepositoryImpl(private val cameraDataSource: CameraDataSource) : CameraRepository {
    override suspend fun getDetectImage(): List<ByteArray> {
        TODO("Not yet implemented")
    }
}