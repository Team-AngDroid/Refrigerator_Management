package com.angdroid.refrigerator_manament.data.datasource.home

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot

interface UserInfoDataSource {
    suspend fun getUserInfo(): Task<DocumentSnapshot>
}