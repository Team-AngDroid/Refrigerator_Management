package com.angdroid.refrigerator_manament.presentation.home.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.application.App
import com.angdroid.refrigerator_manament.data.dto.FoodDto
import com.angdroid.refrigerator_manament.data.mapper.home.UserMapper
import com.angdroid.refrigerator_manament.databinding.FragmentRefrigeratorBinding
import com.angdroid.refrigerator_manament.domain.entity.model.IngredientType
import com.angdroid.refrigerator_manament.presentation.DynamicGridLayoutManager
import com.angdroid.refrigerator_manament.presentation.home.CategoryListAdapter
import com.angdroid.refrigerator_manament.presentation.home.IngredientViewModel
import com.angdroid.refrigerator_manament.presentation.util.BaseFragment
import com.angdroid.refrigerator_manament.util.collectFlowWhenStarted
import com.google.firebase.firestore.SetOptions
import com.startup.meetiing.presentation.state.UiState
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class RefrigeratorFragment :
    BaseFragment<FragmentRefrigeratorBinding>(R.layout.fragment_refrigerator) {
    private lateinit var adapter: CategoryListAdapter
    private val ingredientViewModel: IngredientViewModel by viewModels()
    private lateinit var mapper: UserMapper
    private val list = mapOf(
        "foodInfo" to listOf<FoodDto>(
            FoodDto("0", 107, "", "계란란", "", 3, 3),
            FoodDto("1", 107, "", "계란", "", 3, 6),
            FoodDto("2", 107, "", "계란", "", 3, 1),
            FoodDto("3", 107, "", "계란", "", 3, 2),
            FoodDto("4", 107, "", "계란", "", 3, 6),
            FoodDto("5", 103, "", "무무", "", 1, 4),
            FoodDto("6", 103, "", "무우", "", 1, 1),
            FoodDto("7", 103, "", "무우", "", 1, 2),
            FoodDto("8", 103, "", "무우", "", 1, 3),
            FoodDto("9", 104, "", "사과", "", 2, 4),
            FoodDto("10", 105, "", "배", "", 2, 1),
            FoodDto("11", 106, "", "귤", "", 2, 1),
            FoodDto("12", 110, "", "치즈", "", 4, 1),
            FoodDto("13", 111, "", "새우", "", 5, 1),
            FoodDto("14", 112, "", "오징어", "", 5, 2),
            FoodDto("15", 113, "", "고등어", "", 5, 3),
            FoodDto("16", 109, "", "우유", "", 4, 1),
            FoodDto("17", 108, "", "생닭", "", 3, 1),
            FoodDto("18", 101, "", "당근", "", 1, 4),
            FoodDto("19", 102, "", "오이", "", 1, 4),
            FoodDto("20", 102, "", "오이", "", 1, 4),
            FoodDto("21", 102, "", "오이", "", 1, 4)
        )
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        mapper = UserMapper()
        App.fireStoreUserReference.set(list, SetOptions.merge())
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