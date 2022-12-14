package com.angdroid.refrigerator_manament.data.repository

import android.util.Log
import com.angdroid.refrigerator_manament.application.App
import com.angdroid.refrigerator_manament.data.controller.FoodInfoController
import com.angdroid.refrigerator_manament.data.controller.StorageController
import com.angdroid.refrigerator_manament.data.datasource.recipe.RecipeDataSource
import com.angdroid.refrigerator_manament.data.datasource.user.UserInfoDataSource
import com.angdroid.refrigerator_manament.data.dto.FoodDto
import com.angdroid.refrigerator_manament.data.dto.RecipeDto
import com.angdroid.refrigerator_manament.data.mapper.recipe.RecipeMapper
import com.angdroid.refrigerator_manament.data.mapper.user.UserMapper
import com.angdroid.refrigerator_manament.domain.entity.RecipeEntity
import com.angdroid.refrigerator_manament.domain.entity.model.IngredientType
import com.angdroid.refrigerator_manament.domain.repository.FireBaseRepository
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import javax.inject.Inject

class FireBaseRepositoryImpl @Inject constructor(
    private val storageController: StorageController,
    private val foodInfoController: FoodInfoController,
    private val userInfoDataSource: UserInfoDataSource,
    private val recipeDataSource: RecipeDataSource,
    private val recipeMapper: RecipeMapper,
    private val userMapper: UserMapper
) : FireBaseRepository {
    override suspend fun getAllRecipe(): List<RecipeEntity> {
        recipeDataSource.getAllRecipe().await().let { documents ->
            return recipeMapper.mapToEntity(documents.map {
                it.toObject(RecipeDto::class.java)
            })
        }
    }

    /**
     * 재료뿐만 아니라 레시피 이름으로도 검색할 수 있도록 name field를 통해서 검색
     */

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
    ): List<RecipeEntity> {
        recipeDataSource.getSearchRecipe(name).await().let { documents ->
            return recipeMapper.mapToEntity(documents.map {
                it.toObject(RecipeDto::class.java)
            }.filter { it.name.contains(name) })
        }
    }

    /**
     * AutoCompleteTextView에 사용할 레시피 이름들을 파이어베이스에서 받아오는 함수
     */
    override suspend fun getRecipeNameList(): List<String> {
        recipeDataSource.getRecipeNameList().await().let { documents ->
            return documents.map { document -> document["name"] as String }
        }
    }

    override suspend fun getFoodList(): ArrayList<IngredientType>? =
        userInfoDataSource.getUserInfo().await().let { documents ->
            val now = LocalDate.now()
            (documents.data?.get("foodInfo") as ArrayList<HashMap<String, *>>?)?.map { result ->
                FoodDto(
                    (result["id"] as String),
                    ((result["foodId"] as Long).toInt()),
                    (result["expirationDate"] as String),
                    (result["name"] as String),
                    (result["image"] as String?),
                    ((result["categoryId"] as Long).toInt()),
                    ((result["foodCount"] as Long).toInt())
                )
            }?.let { dto ->
                userMapper.mapToEntity(dto).filter { it.expirationDate >= now }.run {
                    setFoodInfo(this)
                    this as ArrayList<IngredientType>
                }
            }
        }


    override suspend fun getFood(
        ingredient: String
    ) = (userInfoDataSource.getUserInfo()
        .await().data?.get("foodInfo") as ArrayList<HashMap<String, *>>?)?.map { result ->
        FoodDto(
            (result["id"] as String),
            ((result["foodId"] as Long).toInt()),
            (result["expirationDate"] as String),
            (result["name"] as String),
            (result["image"] as String?),
            ((result["categoryId"] as Long).toInt()),
            ((result["foodCount"] as Long).toInt())
        )
    }?.let {
        userMapper.mapToEntity(
            it
        ).filter { item -> item.name == ingredient }
    }

    override suspend fun addIngredients(
        ingredients: List<IngredientType.Food>
    ) {
        (userInfoDataSource.getUserInfo()
            .await().data?.get("foodInfo") as ArrayList<HashMap<String, *>>?)?.map { result ->
            FoodDto(
                (result["id"] as String),
                ((result["foodId"] as Long).toInt()),
                (result["expirationDate"] as String),
                (result["name"] as String),
                (result["image"] as String?),
                ((result["categoryId"] as Long).toInt()),
                ((result["foodCount"] as Long).toInt())
            )
        }.let {
            userInfoDataSource.setFoodInfo((it ?: listOf()).plus(userMapper.mapToDto(ingredients)))
        }
    }

    override suspend fun upLoadFoodImage(paths: List<String>, byteArrayImages: List<ByteArray?>) {
        storageController.upLoadFoodImage(paths, byteArrayImages)
    }

    override suspend fun setFoodInfo(foodList: List<IngredientType.Food>) {
        userInfoDataSource.setFoodInfo(userMapper.mapToDto(foodList))
    }
}