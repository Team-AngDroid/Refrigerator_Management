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
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
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

    override suspend fun getAllRecipe(): List<RecipeEntity> {
        recipeDataSource.getAllRecipe().await().let { documents ->
            return recipeMapper.mapToEntity(documents.map { document -> document.toObject(RecipeDto::class.java) })
        }
    }

    override suspend fun getIngredientRecipe(
        ingredient: String
    ): List<RecipeEntity> {
        recipeDataSource.getIngredientRecipe(ingredient).await().let { documents ->
            return recipeMapper.mapToEntity(documents.map {
                it.toObject(RecipeDto::class.java)
            })
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
                if ((document["name"] as String).contains(name)) {
                    result.add(document.toObject(RecipeDto::class.java))
                }
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

    override suspend fun getFoodList(): ArrayList<IngredientType> {
        val now = LocalDate.now()
        userInfoDataSource.getUserInfo().await().let {
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
            }).filter { it.expirationDate >= now }.run {
                userInfoDataSource.setFoodInfo(userMapper.mapToDto(this))
                return this as ArrayList<IngredientType>
            }
        }
    }

    override suspend fun getFood(
        ingredient: String
    ) = userMapper.mapToEntity(
        (userInfoDataSource.getUserInfo()
            .await().data?.get("foodInfo") as ArrayList<HashMap<String, *>>).map { result ->
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

    override suspend fun addIngredients(
        ingredients: List<IngredientType>,
        onApiResult: (Boolean) -> Unit
    ) {/*
        App.fireStoreUserReference.update("foodInfo",FieldValue.arrayUnion()
        )
            .addOnSuccessListener {
                onApiResult(true)
            }.addOnFailureListener { e ->
                onApiResult(false)
                throw Exception(e.message)
            } 나중에 할래..*/
    }
}