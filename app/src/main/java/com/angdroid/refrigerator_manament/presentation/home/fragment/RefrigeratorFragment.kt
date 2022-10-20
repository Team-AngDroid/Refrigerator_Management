package com.angdroid.refrigerator_manament.presentation.home.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.application.App
import com.angdroid.refrigerator_manament.data.dto.FoodDto
import com.angdroid.refrigerator_manament.databinding.FragmentRefrigeratorBinding
import com.angdroid.refrigerator_manament.presentation.DynamicGridLayoutManager
import com.angdroid.refrigerator_manament.presentation.home.CategoryListAdapter
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
    private val list = mapOf(
        "foodInfo" to listOf<FoodDto>(
            FoodDto("0", 107, LocalDate.now().toString(), "계란", "", 3, 3),
            FoodDto("1", 107, LocalDate.now().toString(), "계란", "", 3, 6),
            FoodDto("2", 107, LocalDate.now().toString(), "계란", "", 3, 1),
            FoodDto("3", 107, LocalDate.now().toString(), "계란", "", 3, 2),
            FoodDto("4", 107, LocalDate.now().toString(), "계란", "", 3, 6),
            FoodDto("5", 103, LocalDate.now().toString(), "무우", "", 1, 4),
            FoodDto("6", 103, LocalDate.now().toString(), "무우", "", 1, 1),
            FoodDto("7", 103, LocalDate.now().toString(), "무우", "", 1, 2),
            FoodDto("8", 103, LocalDate.now().toString(), "무우", "", 1, 3),
            FoodDto("9", 104, LocalDate.now().toString(), "사과", "", 2, 4),
            FoodDto("10", 105, LocalDate.now().toString(), "배", "", 2, 1),
            FoodDto("11", 106, LocalDate.now().toString(), "귤", "", 2, 1),
            FoodDto("12", 110, LocalDate.now().toString(), "치즈", "", 4, 1),
            FoodDto("13", 111, LocalDate.now().toString(), "새우", "", 5, 1),
            FoodDto("14", 112, LocalDate.now().toString(), "오징어", "", 5, 2),
            FoodDto("15", 113, LocalDate.now().toString(), "고등어", "", 5, 3),
            FoodDto("16", 109, LocalDate.now().toString(), "우유", "", 4, 1),
            FoodDto("17", 108, LocalDate.now().toString(), "생닭", "", 3, 1),
            FoodDto("18", 101, LocalDate.now().toString(), "당근", "", 1, 4),
            FoodDto("19", 102, LocalDate.now().toString(), "오이", "", 1, 4),
            FoodDto("20", 102, LocalDate.now().toString(), "오이", "", 1, 4),
            FoodDto("21", 102, LocalDate.now().toString(), "오이", "", 1, 4)
        )
    )
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.fireStoreUserReference.set(list) //FireStore Map 추가 로직! 주석처리하는거 추천~~
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