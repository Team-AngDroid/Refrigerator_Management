package com.angdroid.refrigerator_manament.presentation.camera

import android.os.Bundle
import androidx.activity.viewModels
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.databinding.ActivityAddIngredientBinding
import com.angdroid.refrigerator_manament.domain.entity.model.IngredientType
import com.angdroid.refrigerator_manament.presentation.camera.adapter.AddIngredientAdapter
import com.angdroid.refrigerator_manament.presentation.camera.viewmodel.CameraViewModel
import com.angdroid.refrigerator_manament.presentation.custom.CustomDialog
import com.angdroid.refrigerator_manament.presentation.util.BaseActivity
import com.angdroid.refrigerator_manament.util.collectFlowWhenStarted
import timber.log.Timber
import java.time.LocalDate

class AddIngredientActivity :
    BaseActivity<ActivityAddIngredientBinding>(R.layout.activity_add_ingredient) {

    private lateinit var adapter: AddIngredientAdapter
    private val cameraViewModel: CameraViewModel by viewModels()

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
            itemDeleteListener = {
                cameraViewModel.removeItem(it)
            },
            itemMinusListener = {
                cameraViewModel.minusItemCount(it)
            },
            itemPlusListener = {
                cameraViewModel.plusItemCount(it)
            },
            itemDialogListener = {
                CustomDialog(this, cameraViewModel).showDialog()
            }
        )
        binding.rcvIngredients.adapter = adapter
        collectFoodList()
    }

    private fun collectFoodList() {
        collectFlowWhenStarted(cameraViewModel.foodList) {
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

}