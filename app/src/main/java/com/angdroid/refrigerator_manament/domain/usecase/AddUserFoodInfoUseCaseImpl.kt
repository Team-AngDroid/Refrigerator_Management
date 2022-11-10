package com.angdroid.refrigerator_manament.domain.usecase

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.angdroid.refrigerator_manament.domain.entity.model.IngredientType
import com.angdroid.refrigerator_manament.domain.repository.FireBaseRepository
import java.io.ByteArrayOutputStream
import java.util.*
import javax.inject.Inject

class AddUserFoodInfoUseCaseImpl @Inject constructor(private val fireBaseRepository: FireBaseRepository) :
    AddUserFoodInfoUseCase {
    override suspend fun invoke(
        ingredients: List<IngredientType.Food>
    ) {
        val images = ingredients.map { food ->
            if (food.image != "" && food.image != null) {
                val bitmap =
                    BitmapFactory.decodeFile("/data/user/0/com.angdroid.refrigerator_manament/cache/${food.image}")
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream)
                bitmap.recycle()
                stream.toByteArray()
            } else {
                null
            }
        }

        val imagePath =
            images.map { if (it != null) UUID.nameUUIDFromBytes(it).toString() else "" }
        fireBaseRepository.upLoadFoodImage(imagePath, images)
        fireBaseRepository.addIngredients(ingredients.mapIndexed { index, food ->
            IngredientType.Food(
                UUID.randomUUID().toString(),
                food.foodId,
                food.expirationDate,
                food.name,
                imagePath[index],
                food.categoryId,
                food.foodCount
            )
        })
    }
}
