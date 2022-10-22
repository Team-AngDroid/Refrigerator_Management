package com.angdroid.refrigerator_manament.presentation.home.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.databinding.FragmentSearchBinding
import com.angdroid.refrigerator_manament.presentation.util.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

}