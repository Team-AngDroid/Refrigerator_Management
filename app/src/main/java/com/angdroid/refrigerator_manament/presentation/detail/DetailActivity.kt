package com.angdroid.refrigerator_manament.presentation.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.window.layout.WindowMetrics
import androidx.window.layout.WindowMetricsCalculator
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.databinding.ActivityDetailBinding
import com.angdroid.refrigerator_manament.domain.entity.model.IngredientType
import com.angdroid.refrigerator_manament.presentation.custom.CustomWebView
import com.angdroid.refrigerator_manament.presentation.detail.adapter.DetailIngredientListAdapter
import com.angdroid.refrigerator_manament.presentation.detail.adapter.DetailRecipeListAdapter
import com.angdroid.refrigerator_manament.presentation.util.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : BaseActivity<ActivityDetailBinding>(R.layout.activity_detail) {

    private val detailViewModel: DetailViewModel by viewModels()
    private lateinit var detailListAdapter: DetailRecipeListAdapter
    private lateinit var ingredientDetailListAdapter: DetailIngredientListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.detailViewModel = detailViewModel
        detailListAdapter = DetailRecipeListAdapter() {
            startActivity(Intent(this, CustomWebView::class.java)
                .apply { putExtra("link", it.link) }
            )
        }
        val windowMetrics: WindowMetrics =
            WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this)
        ingredientDetailListAdapter = DetailIngredientListAdapter(windowMetrics)

        binding.rcRecipe.adapter = detailListAdapter
        binding.rvIngredient.adapter = ingredientDetailListAdapter
        intent.getParcelableExtra<IngredientType.Food>("foodName")?.let { food ->
            detailViewModel.selectItem.value = food
            detailViewModel.getIngredientRecipe(food.name)
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
}