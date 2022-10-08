package com.angdroid.refrigerator_manament.data.repository.camera

import com.angdroid.refrigerator_manament.data.datasource.camera.CameraDataSource
import com.angdroid.refrigerator_manament.domain.repository.CameraRepository
import javax.inject.Inject

class CameraRepositoryImpl @Inject constructor(private val cameraDataSource: CameraDataSource) : CameraRepository {
    override suspend fun getDetectImage(): List<ByteArray> {
        TODO("Not yet implemented")
    }
}