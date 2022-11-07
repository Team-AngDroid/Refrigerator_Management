package com.angdroid.refrigerator_manament.presentation.camera

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.databinding.ActivityAddIngredientBinding
import com.angdroid.refrigerator_manament.domain.entity.model.IngredientType
import com.angdroid.refrigerator_manament.presentation.camera.adapter.AddIngredientAdapter
import com.angdroid.refrigerator_manament.presentation.camera.viewmodel.AddIngredientViewModel
import com.angdroid.refrigerator_manament.presentation.custom.CustomDialog
import com.angdroid.refrigerator_manament.presentation.util.BaseActivity
import com.angdroid.refrigerator_manament.presentation.util.safeLet
import com.angdroid.refrigerator_manament.presentation.util.safeValueOf
import com.angdroid.refrigerator_manament.presentation.util.types.FoodIdType
import com.angdroid.refrigerator_manament.util.collectFlowWhenStarted
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*

class AddIngredientActivity :
    BaseActivity<ActivityAddIngredientBinding>(R.layout.activity_add_ingredient) {

    private lateinit var adapter: AddIngredientAdapter
    private val addIngredientViewModel: AddIngredientViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        intent?.let {
            addIngredientViewModel.setIntentFoodList(getIngredients())
        }
        setAppbar()
        initAdapter()
        //TODO 해당 람다에서 추가해놓은 FireStore, Storage 에 접근해야함
        binding.btnIngredientsAdd.setOnClickListener {
            try {
                cacheDir.listFiles()?.map { file -> file.delete() }
                Toast.makeText(this, "삭제 성공", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "삭제 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initBinding() {
        binding.lifecycleOwner = this
        binding.addIngredientViewModel = addIngredientViewModel
    }

    private fun setAppbar() {
        binding.appbarIngredient.topAppbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.top_back -> {
                    finish()
                    true
                }
                else -> false
            }
        }
    }

    private fun initAdapter() {
        adapter = AddIngredientAdapter(
            this,
            itemDeleteListener = {
                addIngredientViewModel.removeItem(it)
            },
            itemMinusListener = {
                addIngredientViewModel.minusItemCount(it)
            },
            itemPlusListener = {
                addIngredientViewModel.plusItemCount(it)
            },
            itemDialogListener = {
                CustomDialog(this, itemAddListener = {
                    addIngredientViewModel.addDialogFood(it)
                }).showDialog()
            }
        )
        binding.rcvIngredients.adapter = adapter
        collectFoodList()
    }

    private fun collectFoodList() {
        collectFlowWhenStarted(addIngredientViewModel.foodList) {
            adapter.submitList(it.toList())
        }
    }

    private fun getIngredients(): List<IngredientType.Food> {
        safeLet(
            intent.getStringArrayListExtra("ingredients"),
            intent.getStringArrayListExtra("nameList")
        ) { fileList, nameList ->
            val ingredients = fileList.mapIndexed { index, file ->
                safeValueOf<FoodIdType>(nameList[index])?.let { food ->
                    IngredientType.Food(
                        fid = (index + 1).toString(),
                        foodId = food.foodId,
                        expirationDate = LocalDate.now(),
                        name = food.name,
                        image = file,
                        categoryId = food.returnCategoryType(),
                        foodCount = 1
                    )
                }
            }.filterNotNull().toMutableList()
            ingredients.add(
                0, IngredientType.Food("0", 0, LocalDate.now(), "", "", 0, 0)
                // 맨처음 [직접 추가]아이템용 더미데이터
            )
            Log.e("GetIngredients", ingredients.toString())
            return ingredients
        }
        return emptyList()
    }

    /**
     * List<FilePath: String> 타입을 List<Bitmap>으로 변경해주는 Converter
     */
    private fun fileToBitmapConverter(fileList: List<String>): List<Bitmap> {
        return try {
            fileList.map { imagePath ->
                BitmapFactory.decodeFile("$cacheDir/$imagePath")
            }
        } catch (_: java.lang.Exception) {
            listOf()
        }
    }

    /**
     * List<ByteArray: Image> 타입을 List<Bitmap>으로 변경해주는 Converter
     */
    private fun byteArrayToBitmap(byteArrays: List<ByteArray>): List<Bitmap?> {
        return byteArrays.map { BitmapFactory.decodeByteArray(it, 0, it.size) }
    }
}