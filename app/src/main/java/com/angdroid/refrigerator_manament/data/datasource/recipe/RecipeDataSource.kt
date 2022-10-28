package com.angdroid.refrigerator_manament.data.datasource.recipe

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

interface RecipeDataSource {
    suspend fun getAllRecipe(): Task<QuerySnapshot>
    suspend fun getIngredientRecipe(food:String):Task<QuerySnapshot>
    suspend fun getSearchRecipe(name:String):Task<QuerySnapshot>
    suspend fun getRecipeNameList():Task<QuerySnapshot>
}