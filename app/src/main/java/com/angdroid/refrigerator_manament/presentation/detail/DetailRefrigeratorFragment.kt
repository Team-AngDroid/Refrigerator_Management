package com.angdroid.refrigerator_manament.presentation.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.databinding.FragmentDetailRefrigeratorBinding
import com.angdroid.refrigerator_manament.presentation.util.BaseFragment
import com.angdroid.refrigerator_manament.util.collectFlowWhenStarted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailRefrigeratorFragment : BaseFragment<FragmentDetailRefrigeratorBinding>(R.layout.fragment_detail_refrigerator) {

    private val detailViewModel : DetailViewModel by viewModels()
    private lateinit var detailListAdapter: DetailListAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.detailViewModel = detailViewModel
        detailListAdapter = DetailListAdapter()
        binding.rcRecipe.adapter = detailListAdapter
        collectData()
        detailViewModel.getAllRecipe()
    }

    private fun collectData() {
        collectFlowWhenStarted(detailViewModel.recipeList){
            detailListAdapter.submitList(it)
        }
    }
}