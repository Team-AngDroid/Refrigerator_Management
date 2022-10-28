package com.angdroid.refrigerator_manament.data.datasource.recipe

import com.angdroid.refrigerator_manament.application.App
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject

class RecipeDataSourceImpl @Inject constructor() : RecipeDataSource {
    override suspend fun getAllRecipe(): Task<QuerySnapshot> = App.fireStoreRecipeReference.get()
    override suspend fun getIngredientRecipe(food: String): Task<QuerySnapshot> =App.fireStoreRecipeReference.whereArrayContains("foodlist", food).get()
    override suspend fun getSearchRecipe(name: String): Task<QuerySnapshot> =App.fireStoreRecipeReference.whereEqualTo("name", name).get()
    override suspend fun getRecipeNameList(): Task<QuerySnapshot> = App.fireStoreRecipeReference.get()
}