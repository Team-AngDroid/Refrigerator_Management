package com.angdroid.refrigerator_manament.domain.repository

interface CameraRepository {
    suspend fun getDetectImage():List<ByteArray>
}