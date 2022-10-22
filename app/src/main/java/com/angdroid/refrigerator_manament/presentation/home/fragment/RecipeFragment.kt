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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etSearch.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_recipe_to_searchFragment)
        }
    }
}
