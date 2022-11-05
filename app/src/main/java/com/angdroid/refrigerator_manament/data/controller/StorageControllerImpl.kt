package com.angdroid.refrigerator_manament.data.controller

import com.google.firebase.storage.StorageReference
import java.util.*
import javax.inject.Inject

class StorageControllerImpl @Inject constructor(private val storage: StorageReference) :
    StorageController {
    override suspend fun upLoadFoodImage(paths: List<String>, byteArrayImages: List<ByteArray>) {
        paths.forEachIndexed { index, path ->
            storage.child("/$path").putBytes(byteArrayImages[index])
        }
    }
}