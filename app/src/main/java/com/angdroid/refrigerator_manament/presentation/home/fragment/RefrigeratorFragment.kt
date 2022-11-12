package com.angdroid.refrigerator_manament.presentation.home.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.databinding.FragmentRefrigeratorBinding
import com.angdroid.refrigerator_manament.presentation.custom.DynamicGridLayoutManager
import com.angdroid.refrigerator_manament.presentation.detail.DetailActivity
import com.angdroid.refrigerator_manament.presentation.home.adapter.CategoryListAdapter
import com.angdroid.refrigerator_manament.presentation.home.viewmodel.IngredientViewModel
import com.angdroid.refrigerator_manament.presentation.util.BaseFragment
import com.angdroid.refrigerator_manament.presentation.util.getSpanSizeLookUp
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RefrigeratorFragment :
    BaseFragment<FragmentRefrigeratorBinding>(R.layout.fragment_refrigerator) {
    private lateinit var adapter: CategoryListAdapter
    private val ingredientViewModel: IngredientViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.ingredientViewModel = ingredientViewModel
        setAdapter()
    }

    private fun setAdapter() {
        adapter = CategoryListAdapter { item ->
            activity?.startActivity(
                Intent(
                    requireContext(),
                    DetailActivity::class.java
                ).apply {
                    this.putExtra("foodName", item)
                })
        }
        binding.rvList.layoutManager =
            DynamicGridLayoutManager(requireContext(), adapter.getSpanSizeLookUp())
        binding.rvList.adapter = adapter
    }
}