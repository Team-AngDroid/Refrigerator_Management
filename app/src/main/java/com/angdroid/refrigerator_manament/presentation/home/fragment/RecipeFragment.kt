package com.angdroid.refrigerator_manament.presentation.home.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.databinding.FragmentRecipeBinding
import com.angdroid.refrigerator_manament.presentation.custom.CustomWebView
import com.angdroid.refrigerator_manament.presentation.home.viewmodel.RecipeViewModel
import com.angdroid.refrigerator_manament.presentation.home.adapter.RecipeAdapter
import com.angdroid.refrigerator_manament.presentation.util.BaseFragment
import com.angdroid.refrigerator_manament.presentation.util.setOnSingleClickListener
import com.angdroid.refrigerator_manament.util.collectFlowWhenStarted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeFragment :
    BaseFragment<FragmentRecipeBinding>(R.layout.fragment_recipe) {

    private val recipeViewModel: RecipeViewModel by activityViewModels()

    private lateinit var recipeAdapter: RecipeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.reciepviewmodel = recipeViewModel // 데이터바인딩
        binding.lifecycleOwner = this
        collectList()
        setAdapter()
        setListener()
    }

    private fun setListener() {
        binding.cvRecommend1.setOnSingleClickListener {
            startActivity(Intent(requireContext(), CustomWebView::class.java)
                .apply { putExtra("link", recipeViewModel.randomRecipeList.value[0].link) }
            )
        }
        binding.cvRecommend2.setOnSingleClickListener {
            startActivity(Intent(requireContext(), CustomWebView::class.java)
                .apply { putExtra("link", recipeViewModel.randomRecipeList.value[1].link) }
            )
        }

        binding.etSearch.setOnClickListener {
            findNavController().navigate(R.id.action_recipeFragment_to_searchFragment)
        }
    }

    private fun setAdapter() {
        recipeAdapter = RecipeAdapter(requireContext()) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it.link)))
        }
        binding.rcvRecipe.adapter = recipeAdapter
    }

    private fun collectList() {
        collectFlowWhenStarted(recipeViewModel.randomIngredientRecipeList) {
            recipeAdapter.submitList(it)
        }
    }
}


