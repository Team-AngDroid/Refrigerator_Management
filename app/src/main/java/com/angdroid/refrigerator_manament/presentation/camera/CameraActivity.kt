package com.angdroid.refrigerator_manament.presentation.camera

import android.content.Intent
import android.os.Bundle
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.databinding.ActivityCameraBinding
import com.angdroid.refrigerator_manament.domain.entity.model.IngredientType
import com.angdroid.refrigerator_manament.presentation.util.BaseActivity
import java.time.LocalDate

class CameraActivity : BaseActivity<ActivityCameraBinding>(R.layout.activity_camera) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val foodList = arrayListOf<IngredientType.Food>(
//            IngredientType.Food("123", 107, LocalDate.now(), "계란", "", 3, 6),
//            IngredientType.Food("11", 103, LocalDate.now(), "무우", "", 1, 4),
//            IngredientType.Food("12", 104, LocalDate.now(), "사과", "", 2, 4),
//            IngredientType.Food("13", 105, LocalDate.now(), "배", "", 2, 1),
//            IngredientType.Food("3", 106, LocalDate.now(), "귤", "", 2, 1),
//            IngredientType.Food("2", 110, LocalDate.now(), "치즈", "", 4, 1),
//            IngredientType.Food("4", 111, LocalDate.now(), "새우", "", 5, 1),
//            IngredientType.Food("5", 112, LocalDate.now(), "오징어", "", 5, 2),
//            IngredientType.Food("6", 113, LocalDate.now(), "고등어", "", 5, 3),
//            IngredientType.Food("7", 109, LocalDate.now(), "우유", "", 4, 1),
//            IngredientType.Food("8", 108, LocalDate.now(), "생닭", "", 3, 1),
//            IngredientType.Food("9", 101, LocalDate.now(), "당근", "", 1, 4),
//            IngredientType.Food("10", 102, LocalDate.now(), "오이", "", 1, 4)

            IngredientType.Food("1", 107, LocalDate.now(), "계란", "", 3, 6),
            IngredientType.Food("2", 103, LocalDate.now(), "무우", "", 1, 4),
            IngredientType.Food("3", 104, LocalDate.now(), "사과", "", 2, 4),
            IngredientType.Food("4", 105, LocalDate.now(), "배", "", 2, 1),
            IngredientType.Food("5", 106, LocalDate.now(), "귤", "", 2, 1),
            IngredientType.Food("6", 110, LocalDate.now(), "치즈", "", 4, 1),
            IngredientType.Food("7", 111, LocalDate.now(), "새우", "", 5, 1),
            IngredientType.Food("8", 112, LocalDate.now(), "오징어", "", 5, 2),
            IngredientType.Food("9", 113, LocalDate.now(), "고등어", "", 5, 3),
            IngredientType.Food("10", 109, LocalDate.now(), "우유", "", 4, 1),
            IngredientType.Food("11", 108, LocalDate.now(), "생닭", "", 3, 1),
            IngredientType.Food("12", 101, LocalDate.now(), "당근", "", 1, 4),
            IngredientType.Food("13", 102, LocalDate.now(), "오이", "", 1, 4)
        )
        startActivity(
            Intent(this@CameraActivity, AddIngredientActivity::class.java)
                .apply { putExtra("Ingredients", foodList) })
        finish() // 백스택에서 사라지도록
    }
}