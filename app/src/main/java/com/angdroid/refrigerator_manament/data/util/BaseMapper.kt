package com.angdroid.refrigerator_manament.data.util

interface BaseMapper<F,T> {
    fun map(from: F): T
}