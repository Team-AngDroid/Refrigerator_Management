package com.angdroid.refrigerator_manament.presentation.camera

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.databinding.ActivityCameraBinding
import com.angdroid.refrigerator_manament.domain.entity.model.IngredientType
import com.angdroid.refrigerator_manament.presentation.util.BaseActivity
import com.angdroid.refrigerator_manament.presentation.util.types.FoodTypeFeatures
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDate


class CameraActivity : BaseActivity<ActivityCameraBinding>(R.layout.activity_camera) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val foodList = arrayListOf<IngredientType.Food>(
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
        val filePath = saveBitmapToJpeg(
            FoodTypeFeatures.values()
                .map { value -> BitmapFactory.decodeResource(resources, value.imageRes) })
        startActivity(
            Intent(this@CameraActivity, AddIngredientActivity::class.java)
                .apply {
                    putStringArrayListExtra("ingredients", filePath)
                    putStringArrayListExtra(
                        "nameList",
                        FoodTypeFeatures.values().map { it.name } as ArrayList<String>)
                })

    }

    private fun bitmapToByteArray(bitmaps: List<Bitmap>): List<ByteArray> {
        val stream = ByteArrayOutputStream()
        return bitmaps.map { bitmap ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream)
            bitmap.recycle()
            stream.toByteArray()
        }
    }

    private fun saveBitmapToJpeg(bitmaps: List<Bitmap>): ArrayList<String> {   // 선택한 이미지 내부 저장소에 저장
        try {
            val fileList = bitmaps.mapIndexed { index, bitmap ->
                Log.e("Save Bitmap Image", cacheDir.absolutePath)
                val tempFile = File(cacheDir, "imageTemp$index") // 파일 경로와 이름 넣기
                tempFile.createNewFile() // 자동으로 빈 파일을 생성하기
                val out = FileOutputStream(tempFile) // 파일을 쓸 수 있는 스트림을 준비하기
                bitmap.compress(
                    Bitmap.CompressFormat.JPEG,
                    80,
                    out
                ) // compress 함수를 사용해 스트림에 비트맵을 저장하기
                bitmap.recycle()
                out.close() // 스트림 닫아주기
                "imageTemp$index"
            }
            Log.e("GetIngredients", fileList.toString())
            return fileList as ArrayList<String>
        } catch (e: Exception) {
            return arrayListOf()
        }
    }

    // Byte를 Bitmap으로 변환
    private fun byteArrayToBitmap(byteArrays: List<ByteArray>): List<Bitmap?> {
        return byteArrays.map { BitmapFactory.decodeByteArray(it, 0, it.size) }

    }
}