package com.angdroid.refrigerator_manament.presentation.camera

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.databinding.ActivityCameraBinding
import com.angdroid.refrigerator_manament.presentation.util.BaseActivity
import com.angdroid.refrigerator_manament.presentation.util.types.FoodTypeFeatures
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class CameraActivity : BaseActivity<ActivityCameraBinding>(R.layout.activity_camera) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(
            Intent(this@CameraActivity, AddIngredientActivity::class.java)
                .apply {
                    putStringArrayListExtra(
                        "ingredients",
                        saveBitmapToJpeg(
                            FoodTypeFeatures.values()
                                .map { value ->
                                    BitmapFactory.decodeResource(
                                        resources,
                                        value.imageRes
                                    )
                                })
                    )
                    putStringArrayListExtra(
                        "nameList",
                        FoodTypeFeatures.values().map { it.name } as ArrayList<String>)
                })
    }

    /**
     * List<Bitmap> 타입을 List<ByteArray>로 변경해주는 Converter Recycle 도 해야함
     */
    private fun bitmapToByteArray(bitmaps: List<Bitmap>): List<ByteArray> {
        val stream = ByteArrayOutputStream()
        return bitmaps.map { bitmap ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream)
            bitmap.recycle()
            stream.toByteArray()
        }
    }

    /**
     * List<Bitmap> 타입을 Internal Cache Directory 에 저장하는 로직, 저장한 File의 Path List를 반환한다.
     */
    private fun saveBitmapToJpeg(bitmaps: List<Bitmap>): ArrayList<String> {
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
                ) // compress 함수를 사용해 스트림에 비트맵을 저장하기 압축률은 80퍼센트 정도만,, 100퍼센트는 좀 클 것 같으니
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
}