package com.angdroid.refrigerator_manament.data.datasource.recipe

import com.angdroid.refrigerator_manament.application.App
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject

class RecipeDataSourceImpl @Inject constructor() : RecipeDataSource {
    override suspend fun getAllRecipe(): Task<QuerySnapshot> = App.fireStoreRecipeReference.get()
    override suspend fun getIngredientRecipe(food: String): Task<QuerySnapshot> =App.fireStoreRecipeReference.whereArrayContains("foodlist", food).get()

}