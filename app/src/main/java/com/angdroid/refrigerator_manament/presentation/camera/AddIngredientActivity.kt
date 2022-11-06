package com.angdroid.refrigerator_manament.presentation.camera

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.databinding.ActivityAddIngredientBinding
import com.angdroid.refrigerator_manament.domain.entity.model.IngredientType
import com.angdroid.refrigerator_manament.presentation.camera.adapter.AddIngredientAdapter
import com.angdroid.refrigerator_manament.presentation.camera.viewmodel.AddIngredientViewModel
import com.angdroid.refrigerator_manament.presentation.custom.CustomDialog
import com.angdroid.refrigerator_manament.presentation.util.BaseActivity
import com.angdroid.refrigerator_manament.presentation.util.types.FoodIdType
import com.angdroid.refrigerator_manament.presentation.util.types.FoodTypeFeatures
import com.angdroid.refrigerator_manament.util.collectFlowWhenStarted
import java.io.ByteArrayOutputStream
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class AddIngredientActivity :
    BaseActivity<ActivityAddIngredientBinding>(R.layout.activity_add_ingredient) {

    private lateinit var adapter: AddIngredientAdapter
    private val addIngredientViewModel: AddIngredientViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()
        intent?.let {
            addIngredientViewModel.setIntentFoodList(getIngredients())
            //getIngredients()
        }
        setAppbar()
        initAdapter()
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
        val newIngredients = FoodTypeFeatures.values().map {
            BitmapFactory.decodeResource(resources, it.imageRes)
        }
        val nameList = intent.getStringArrayListExtra("NameList")!!
        val foodList = newIngredients.mapIndexed { index, s ->
            IngredientType.Food(
                (index+1).toString(),
                FoodIdType.valueOf(nameList[index]).foodId,
                LocalDate.now(),
                FoodIdType.valueOf(nameList[index]).name,
                s,
                FoodIdType.valueOf(nameList[index]).returnCategoryType().toInt(),
                1
            )
        }.toMutableList()
        foodList.add(
            0, IngredientType.Food("0", 0, LocalDate.now(), "", null, 0, 0)
            // 맨처음 [직접 추가]아이템용 더미데이터
        )
        return foodList
    }

    private fun bitmapToByteArray(bitmaps: List<Bitmap>): List<ByteArray> {
        val stream = ByteArrayOutputStream()
        return bitmaps.map { bitmap ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, stream)
            bitmap.recycle()
            stream.toByteArray()
        }
    }
    // Byte를 Bitmap으로 변환
    private fun byteArrayToBitmap(byteArrays: List<ByteArray>): List<Bitmap?> {
        return byteArrays.map { BitmapFactory.decodeByteArray(it, 0, it.size) }

    }
}