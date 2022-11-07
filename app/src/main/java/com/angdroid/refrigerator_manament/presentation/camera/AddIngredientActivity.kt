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
        lifecycleScope.launch {
            try {
                val imagePath = "$cacheDir/image1" // 내부 저장소에 저장되어 있는 이미지 경로
                val bm = BitmapFactory.decodeFile(imagePath)
                binding.imageeView.setImageBitmap(bm) // 내부 저장소에 저장된 이미지를 이미지뷰에 셋
                Toast.makeText(applicationContext, "파일 로드 성공", Toast.LENGTH_SHORT).show()
            } catch (e: java.lang.Exception) {
                Toast.makeText(applicationContext, "파일 로드 실패", Toast.LENGTH_SHORT).show()
            }
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

        val ingredientsList =
            intent.getParcelableArrayListExtra<IngredientType.Food>("Ingredients")!!
        ingredientsList.add(
            0, IngredientType.Food("0", 0, LocalDate.now(), "", "", 0, 0)
            // 맨처음 [직접 추가]아이템용 더미데이터
        )
        return ingredientsList.toList()
    }

    // Byte를 Bitmap으로 변환
    private fun byteArrayToBitmap(byteArrays: List<ByteArray>): List<Bitmap?> {
        return byteArrays.map { BitmapFactory.decodeByteArray(it, 0, it.size) }

    }
}