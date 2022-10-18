package com.angdroid.refrigerator_manament.data.util

interface BaseMapper<F,T> {
    fun mapToEntity(from: F): T
    fun mapToDto(from: T): F
}