package com.angdroid.refrigerator_manament.presentation.home.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.databinding.FragmentRefrigeratorBinding
import com.angdroid.refrigerator_manament.presentation.DynamicGridLayoutManager
import com.angdroid.refrigerator_manament.presentation.home.CategoryListAdapter
import com.angdroid.refrigerator_manament.presentation.home.IngredientViewModel
import com.angdroid.refrigerator_manament.presentation.util.BaseFragment
import com.angdroid.refrigerator_manament.util.collectFlowWhenStarted
import com.startup.meetiing.presentation.state.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RefrigeratorFragment :
    BaseFragment<FragmentRefrigeratorBinding>(R.layout.fragment_refrigerator) {
    private lateinit var adapter: CategoryListAdapter
    private val ingredientViewModel: IngredientViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
    }

    private fun setAdapter() {
        adapter = CategoryListAdapter({}, {})
        binding.rvList.layoutManager =
            DynamicGridLayoutManager(requireContext(), adapter.getSpanSizeLookUp())
        binding.rvList.adapter = adapter
        collectingIngredientList()
    }

    private fun collectingIngredientList() {
        collectFlowWhenStarted(ingredientViewModel.ingredient) { result ->
            when (result) {
                is UiState.Loading -> {}
                is UiState.Empty -> {}
                is UiState.Success -> {
                    adapter.submitList(result.data)
                }
                is UiState.Error -> {}
                else -> {}
            }
        }
    }

}