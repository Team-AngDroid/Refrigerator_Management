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

    override suspend fun getIngredientRecipe(
        ingredient: String,
        onComplete: (List<RecipeEntity>) -> Unit
    ) {
        recipeDataSource.getIngredientRecipe(ingredient).addOnSuccessListener { documents ->
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

    /**
     * 재료뿐만 아니라 레시피 이름으로도 검색할 수 있도록 name field를 통해서 검색
     */
    override suspend fun getSearchRecipe(
        name: String,
        onComplete: (List<RecipeEntity>) -> Unit
    ) {
        recipeDataSource.getSearchRecipe(name).addOnSuccessListener { documents ->
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

    /**
     * AutoCompleteTextView에 사용할 레시피 이름들을 파이어베이스에서 받아오는 함수
     */
    override suspend fun getRecipeNameList(onComplete: (List<String>) -> Unit) {
        recipeDataSource.getRecipeNameList().addOnSuccessListener { documents ->
            val result = mutableListOf<String>()
            for (document in documents) {
                result.add(document["name"] as String)
            }
            onComplete(result)
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

    override suspend fun getFood(
        ingredient: String,
        onComplete: (List<IngredientType.Food>) -> Unit
    ) {
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
                }).filter { item -> item.name == ingredient }
            )
        }.addOnFailureListener { e ->
            throw Exception(e.message)
        }
    }
}