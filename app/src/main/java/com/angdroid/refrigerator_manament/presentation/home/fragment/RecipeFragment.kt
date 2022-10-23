package com.angdroid.refrigerator_manament.presentation.home.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.databinding.FragmentRecipeBinding
import com.angdroid.refrigerator_manament.domain.entity.RecipeEntity
import com.angdroid.refrigerator_manament.presentation.detail.DetailListAdapter
import com.angdroid.refrigerator_manament.presentation.home.RecipeViewmodel
import com.angdroid.refrigerator_manament.presentation.util.BaseFragment
import com.angdroid.refrigerator_manament.util.collectFlowWhenStarted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeFragment:
    BaseFragment<FragmentRecipeBinding>(R.layout.fragment_recipe) {

    private val recipeViewModel: RecipeViewmodel by viewModels()
    private val recipeList = listOf<RecipeEntity>()
    private lateinit var recipeAdapter: DetailListAdapter

    private val hardRecipeList = listOf<RecipeEntity>(
        RecipeEntity("", "당큰케이크", "20분","","당근", listOf()),
        RecipeEntity("", "과일 사라다", "30분","","사과", listOf()),
        RecipeEntity("", "체다슬라이스칩스", "40분","","치즈", listOf()),
        RecipeEntity("", "무생채", "60분","","무", listOf()),
        RecipeEntity("", "에어프라이어치킨", "30분","","생닭", listOf()),
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //recipeViewModel.getAllRecipe()
        collectData()
        binding.etSearch.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_recipe_to_searchFragment)
        }
    }

    private fun collectData() {

        val set = mutableSetOf<Int>()
        while (set.size < 2){
            set.add((hardRecipeList.indices).random())
        }
        binding.recommend1 = hardRecipeList[set.elementAt(0)]
        binding.recommend2 = hardRecipeList[set.elementAt(1)]

        collectFlowWhenStarted(recipeViewModel.recipeList) {}
    }


}
