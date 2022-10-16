package com.angdroid.refrigerator_manament.data.repository

import com.angdroid.refrigerator_manament.data.controller.FoodInfoController
import com.angdroid.refrigerator_manament.data.datasource.home.UserInfoDataSource
import com.angdroid.refrigerator_manament.data.datasource.recipe.RecipeDataSource
import com.angdroid.refrigerator_manament.data.mapper.home.UserMapper
import com.angdroid.refrigerator_manament.domain.entity.FoodEntity
import com.angdroid.refrigerator_manament.domain.entity.RecipeEntity
import com.angdroid.refrigerator_manament.domain.entity.UserEntity
import com.angdroid.refrigerator_manament.domain.repository.FireBaseRepository
import com.angdroid.refrigerator_manament.presentation.home.model.IngredientType
import java.time.LocalDate
import javax.inject.Inject

class FireBaseRepositoryImpl @Inject constructor(
    private val foodInfoController: FoodInfoController,
    private val userInfoDataSource: UserInfoDataSource,
    private val recipeDataSource: RecipeDataSource,
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
        TODO("Not yet implemented")
    }

    override suspend fun getFoodList(): ArrayList<IngredientType> = arrayListOf(
        IngredientType.Food("123", 107, LocalDate.now(), "계란", "", 3, 6),
        IngredientType.Food("123", 107, LocalDate.now(), "계란", "", 3, 1),
        IngredientType.Food("123", 107, LocalDate.now(), "계란", "", 3, 2),
        IngredientType.Food("123", 107, LocalDate.now(), "계란", "", 3, 6),
        IngredientType.Food("123", 103, LocalDate.now(), "무우", "", 1, 4),
        IngredientType.Food("123", 103, LocalDate.now(), "무우", "", 1, 1),
        IngredientType.Food("123", 103, LocalDate.now(), "무우", "", 1, 2),
        IngredientType.Food("123", 103, LocalDate.now(), "무우", "", 1, 3),
        IngredientType.Food("123", 104, LocalDate.now(), "사과", "", 2, 4),
        IngredientType.Food("123", 105, LocalDate.now(), "배", "", 2, 1),
        IngredientType.Food("123", 106, LocalDate.now(), "귤", "", 2, 1),
        IngredientType.Food("123", 110, LocalDate.now(), "치즈", "", 4, 1),
        IngredientType.Food("123", 111, LocalDate.now(), "새우", "", 5, 1),
        IngredientType.Food("123", 112, LocalDate.now(), "오징어", "", 5, 2),
        IngredientType.Food("123", 113, LocalDate.now(), "고등어", "", 5, 3),
        IngredientType.Food("123", 109, LocalDate.now(), "우유", "", 4, 1),
        IngredientType.Food("123", 108, LocalDate.now(), "생닭", "", 3, 1),
        IngredientType.Food("123", 101, LocalDate.now(), "당근", "", 1, 4),
        IngredientType.Food("123", 102, LocalDate.now(), "오이", "", 1, 4)
    )
}