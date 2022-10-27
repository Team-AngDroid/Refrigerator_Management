package com.angdroid.refrigerator_manament.presentation.home.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.databinding.FragmentRecipeBinding
import com.angdroid.refrigerator_manament.presentation.home.RecipeViewModel
import com.angdroid.refrigerator_manament.presentation.home.adapter.RecipeTitleAdapter
import com.angdroid.refrigerator_manament.presentation.util.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeFragment :
    BaseFragment<FragmentRecipeBinding>(R.layout.fragment_recipe) {

    private val recipeViewModel: RecipeViewModel by activityViewModels()

    private lateinit var recipeAdapter: RecipeTitleAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.reciepviewmodel = recipeViewModel // 데이터바인딩

        setAdapter()
        binding.etSearch.setOnClickListener {
            findNavController().navigate(R.id.action_recipeFragment_to_searchFragment)
        }
    }

    private fun setAdapter() {
        recipeAdapter = RecipeTitleAdapter(requireContext(), {})
        binding.rcvRecipe.adapter = recipeAdapter
        recipeAdapter.submitList(recipeViewModel.randomIngredientRecipeList.value.toList())
    }

}


