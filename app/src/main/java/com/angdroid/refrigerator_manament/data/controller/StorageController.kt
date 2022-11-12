package com.angdroid.refrigerator_manament.data.controller

interface StorageController {
    suspend fun upLoadFoodImage(paths:List<String>, byteArrayImages:List<ByteArray?>)
}