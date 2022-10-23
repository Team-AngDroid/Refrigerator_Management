package com.angdroid.refrigerator_manament.presentation.home.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.databinding.FragmentRefrigeratorBinding
import com.angdroid.refrigerator_manament.presentation.custom.DynamicGridLayoutManager
import com.angdroid.refrigerator_manament.presentation.detail.DetailActivity
import com.angdroid.refrigerator_manament.presentation.home.adapter.CategoryListAdapter
import com.angdroid.refrigerator_manament.presentation.home.IngredientViewModel
import com.angdroid.refrigerator_manament.presentation.util.BaseFragment
import com.angdroid.refrigerator_manament.presentation.util.getSpanSizeLookUp
import com.angdroid.refrigerator_manament.util.collectFlowWhenStarted
import com.startup.meetiing.presentation.state.UiState
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class RefrigeratorFragment :
    BaseFragment<FragmentRefrigeratorBinding>(R.layout.fragment_refrigerator) {
    private lateinit var adapter: CategoryListAdapter
    private val ingredientViewModel: IngredientViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //App.fireStoreUserReference.set(list) //FireStore Map 추가 로직! 주석처리하는거 추천~~
        setAdapter()
    }

    private fun setAdapter() {
        adapter = CategoryListAdapter { item ->
            activity?.startActivity(
                Intent(
                    requireContext(),
                    DetailActivity::class.java
                ).apply {
                    this.putExtra("foodName", item.name)
                })
        }
        binding.rvList.layoutManager =
            DynamicGridLayoutManager(requireContext(), adapter.getSpanSizeLookUp())
        binding.rvList.adapter = adapter
        collectingIngredientList()
    }

    private fun collectingIngredientList() {
        collectFlowWhenStarted(ingredientViewModel.ingredient) { result ->
            when (result) {
                is UiState.Loading -> {
                }
                is UiState.Empty -> {
                }
                is UiState.Success -> {
                    Log.e("Success???", "${result.data}")
                    adapter.submitList(result.data)
                }
                is UiState.Error -> {
                    Log.e("Collect Error", result.error)
                }
                is UiState.Init -> {
                }
            }
        }
    }

}