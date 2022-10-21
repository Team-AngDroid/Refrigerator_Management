package com.angdroid.refrigerator_manament.presentation.camera

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.databinding.ActivityAddIngredientBinding
import com.angdroid.refrigerator_manament.domain.entity.model.IngredientType
import com.angdroid.refrigerator_manament.presentation.camera.adapter.AddIngredientAdapter
import com.angdroid.refrigerator_manament.presentation.camera.viewmodel.CameraViewModel
import com.angdroid.refrigerator_manament.presentation.util.BaseActivity
import com.angdroid.refrigerator_manament.util.collectFlowWhenStarted
import java.time.LocalDate

class AddIngredientActivity :
    BaseActivity<ActivityAddIngredientBinding>(R.layout.activity_add_ingredient) {

    private lateinit var adapter: AddIngredientAdapter
    val cameraViewModel: CameraViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()
        intent?.let {
            cameraViewModel.setIntentFoodList(getIngredients())
        }
        setAppbar()
        initAdapter()
    }

    private fun initBinding() {
        binding.lifecycleOwner = this
        binding.cameraViewModel = cameraViewModel
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
            itemClickListener = {
                cameraViewModel.removeItem(it)
                Log.e("adapter ItemCount", adapter.itemCount.toString())
            },
            itemRemoveListener = {
                cameraViewModel.minusItemCount(it)
            },
        itemAddListener = {
            cameraViewModel.plusItemCount(it)
        }
            )
        binding.rcvIngredients.adapter = adapter
        collectFoodList()
    }

    private fun collectFoodList() {
        collectFlowWhenStarted(cameraViewModel.foodList) {
            adapter.submitList(it.toList())
            Log.e("Result Value List", it.toString())

        }
    }

    private fun getIngredients(): List<IngredientType.Food> {
        val ingredientsList =
            intent.getParcelableArrayListExtra<IngredientType.Food>("Ingredients")!!
        ingredientsList.add(0,
            IngredientType.Food("0", 0, LocalDate.now(), "", "", 0, 0)
            // 마지막 [직접 추가]아이템용 더미데이터
        )
        return ingredientsList.toList()
    }


}