package com.angdroid.refrigerator_manament.presentation.detail

import android.os.Bundle
import androidx.activity.viewModels
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.databinding.ActivityDetailBinding
import com.angdroid.refrigerator_manament.domain.entity.model.IngredientType
import com.angdroid.refrigerator_manament.presentation.util.BaseActivity
import com.angdroid.refrigerator_manament.util.collectFlowWhenStarted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : BaseActivity<ActivityDetailBinding>(R.layout.activity_detail) {

    private val detailViewModel: DetailViewModel by viewModels()
    private lateinit var detailListAdapter: DetailListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.detailViewModel = detailViewModel
        detailListAdapter = DetailListAdapter()
        binding.rcRecipe.adapter = detailListAdapter
        collectData()
        intent.getParcelableExtra<IngredientType.Food>("foodName").let { food ->
            if (food == null) {
                detailViewModel.getAllRecipe()
            } else {
                detailViewModel.selectItem.value = food
                detailViewModel.getIngredientRecipe(food.name)
            }
        }

        binding.appbarIngredientDetail.topAppbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.top_back -> {
                    finish()
                    true
                }
                else -> false
            }
        }
    }

    private fun collectData() {
        collectFlowWhenStarted(detailViewModel.recipeList) {
            detailListAdapter.submitList(it)
        }
    }
}