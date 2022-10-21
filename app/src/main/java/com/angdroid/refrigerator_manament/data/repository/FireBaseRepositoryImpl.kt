package com.angdroid.refrigerator_manament.data.repository

import android.util.Log
import com.angdroid.refrigerator_manament.data.controller.FoodInfoController
import com.angdroid.refrigerator_manament.data.datasource.home.UserInfoDataSource
import com.angdroid.refrigerator_manament.data.datasource.recipe.RecipeDataSource
import com.angdroid.refrigerator_manament.data.dto.FoodDto
import com.angdroid.refrigerator_manament.data.dto.RecipeDto
import com.angdroid.refrigerator_manament.data.mapper.home.UserMapper
import com.angdroid.refrigerator_manament.data.mapper.recipe.RecipeMapper
import com.angdroid.refrigerator_manament.domain.entity.FoodEntity
import com.angdroid.refrigerator_manament.domain.entity.RecipeEntity
import com.angdroid.refrigerator_manament.domain.entity.UserEntity
import com.angdroid.refrigerator_manament.domain.entity.model.IngredientType
import com.angdroid.refrigerator_manament.domain.repository.FireBaseRepository
import javax.inject.Inject

class FireBaseRepositoryImpl @Inject constructor(
    private val foodInfoController: FoodInfoController,
    private val userInfoDataSource: UserInfoDataSource,
    private val recipeDataSource: RecipeDataSource,
    private val recipeMapper: RecipeMapper,
    private val userMapper: UserMapper
) : FireBaseRepository {
    override suspend fun deleteFood(foodDto: FoodEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun addFood(vararg foodDto: FoodEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun getUserInfo(): UserEntity {
        TODO("Not yet implemented")
    }

    override suspend fun getAllRecipe(onComplete: (List<RecipeEntity>) -> Unit) {

        recipeDataSource.getAllRecipe().addOnSuccessListener { documents ->
            val result = mutableListOf<RecipeDto>()
            for (document in documents) {
                Log.e("Result Query", document.data.toString())
                result.add(document.toObject(RecipeDto::class.java))
            }
            onComplete(recipeMapper.mapToEntity(result))
        }.addOnFailureListener { e ->
            throw Exception(e.message)
        }
    }

    override suspend fun getIngredientRecipe(food:String, onComplete: (List<RecipeEntity>) -> Unit) {
        recipeDataSource.getIngredientRecipe(food).addOnSuccessListener { documents ->
            val result = mutableListOf<RecipeDto>()
            for (document in documents) {
                Log.e("Result Query", document.data.toString())
                result.add(document.toObject(RecipeDto::class.java))
            }
            onComplete(recipeMapper.mapToEntity(result))
        }.addOnFailureListener { e ->
            throw Exception(e.message)
        }
    }

    override suspend fun getFoodList(onComplete: (ArrayList<IngredientType>) -> Unit) {
        userInfoDataSource.getUserInfo().addOnSuccessListener {
            onComplete(
                userMapper.mapToEntity((it.data?.get("foodInfo") as ArrayList<HashMap<String, *>>).map { result ->
                    FoodDto(
                        (result["id"] as String),
                        ((result["foodId"] as Long).toInt()),
                        (result["expirationDate"] as String),
                        (result["name"] as String),
                        (result["image"] as String?),
                        ((result["categoryId"] as Long).toInt()),
                        ((result["foodCount"] as Long).toInt())
                    )
                }) as ArrayList<IngredientType>
            )
        }.addOnFailureListener { e ->
            throw Exception(e.message)
        }
    }
}