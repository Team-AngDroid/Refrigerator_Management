package com.angdroid.refrigerator_manament.data.controller

import com.google.firebase.storage.StorageReference
import java.util.*
import javax.inject.Inject

class StorageControllerImpl @Inject constructor(private val storage: StorageReference) :
    StorageController {
    override suspend fun upLoadFoodImage(paths: List<String>, byteArrayImages: List<ByteArray?>) {
        byteArrayImages.forEachIndexed { index, image ->
            if (image != null) {
                storage.child("/${paths[index]}").putBytes(image)
            }
        }
    }
}