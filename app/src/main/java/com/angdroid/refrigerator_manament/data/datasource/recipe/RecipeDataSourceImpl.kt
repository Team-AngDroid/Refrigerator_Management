package com.angdroid.refrigerator_manament.data.datasource.recipe

import com.angdroid.refrigerator_manament.application.App
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject

class RecipeDataSourceImpl @Inject constructor(private val fireStoreRecipeReference: CollectionReference) :
    RecipeDataSource {
    override suspend fun getAllRecipe(): Task<QuerySnapshot> = fireStoreRecipeReference.get()
    override suspend fun getIngredientRecipe(food: String): Task<QuerySnapshot> =
        fireStoreRecipeReference.whereArrayContains("foodlist", food).get()

    override suspend fun getSearchRecipe(name: String): Task<QuerySnapshot> =
        fireStoreRecipeReference.get()

    override suspend fun getRecipeNameList(): Task<QuerySnapshot> =
        fireStoreRecipeReference.get()
}