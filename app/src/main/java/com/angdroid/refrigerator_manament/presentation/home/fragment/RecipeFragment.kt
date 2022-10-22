package com.angdroid.refrigerator_manament.presentation.home.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.databinding.FragmentRecipeBinding
import com.angdroid.refrigerator_manament.presentation.util.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeFragment : BaseFragment<FragmentRecipeBinding>(R.layout.fragment_recipe) {

    private val recipeList = listOf<RecipeData>(
        RecipeData("당큰케이크", "당근", "5분"),
        RecipeData("과일 사라다", "사과", "5분"),
        RecipeData("체다슬라이스칩스", "치즈", "5분"),
        RecipeData("무생채", "무우", "5분"),
        RecipeData("에어프라이어치킨", "생닭", "5분"),
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etSearch.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_recipe_to_searchFragment)
        }
    }
}
