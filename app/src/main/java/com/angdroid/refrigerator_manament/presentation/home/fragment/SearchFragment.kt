package com.angdroid.refrigerator_manament.presentation.home.fragment

import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.databinding.FragmentSearchBinding
import com.angdroid.refrigerator_manament.presentation.detail.adapter.DetailRecipeListAdapter
import com.angdroid.refrigerator_manament.presentation.home.viewmodel.RecipeViewModel
import com.angdroid.refrigerator_manament.presentation.util.BaseFragment
import com.angdroid.refrigerator_manament.util.collectFlowWhenStarted
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private lateinit var autoAdapter: ArrayAdapter<String>
    private lateinit var detailAdapter: DetailRecipeListAdapter
    private val recipeViewModel: RecipeViewModel by activityViewModels()
    private var FLAG: Boolean = true // 최초 진입 시점 확인

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setAdapters()
        setListeners()
    }

    private fun init() {
        binding.autoSearch.requestFocus() //키보드 focus
        val inputMethodManager =
            requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(binding.autoSearch, 0) //키보드 자동으로 올라오도록
        binding.emptySearch = getString(R.string.search_recipe_intro) //초기 문구 설정
    }

    private fun setAdapters() {
        // 두개의 리싸이클러뷰에서 텍스트뷰, 리싸이클러뷰를 두고 visibility와 text 내용을 조절하는 것으로 변경
        detailAdapter = DetailRecipeListAdapter() {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it.link)))
        } // 암시적 인텐트를 통한 링크
        binding.rcvSearch.adapter = detailAdapter
        binding.searching = true

        autoAdapter = ArrayAdapter<String>(requireContext(), R.layout.spinner_list, mutableListOf())
        binding.autoSearch.setAdapter(autoAdapter)

        recipeViewModel.getRecipeNameList()
        collectFlowWhenStarted(recipeViewModel.recipeNameList) {
            autoAdapter.addAll(it)
        }
    }

    private fun setListeners() {
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.layoutEtSearch.setStartIconOnClickListener {
            searchRecipe(binding.autoSearch.text.toString())
            if (FLAG) { //FLAG를 통해서 collectSearchData는 딱한번 불리도록 설정
                collectSearchData()
            }
            FLAG = false // 검색버튼을 누르는 순간 초기 소개 문구는 보이지 않도록 설정
        }

        binding.autoSearch.setOnKeyListener { _, keyCode, _ -> // enter키 이벤트
            if ((keyCode == KeyEvent.KEYCODE_ENTER)) {
                searchRecipe(binding.autoSearch.text.toString())
                if (FLAG) { //FLAG를 통해서 collectSearchData는 딱한번 불리도록 설정
                    collectSearchData()
                }
                FLAG = false // 검색버튼을 누르는 순간 초기 소개 문구는 보이지 않도록 설정
                true
            } else {
                false
            }
        }

    }

    private fun searchRecipe(search: String) { // 검색함수
        if (search != "") recipeViewModel.getIngredientSearchRecipe(search)
        val inputMethodManager =
            requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.autoSearch.windowToken, 0) //키보드 내리기
        binding.autoSearch.text = null
    }

    private fun collectSearchData() {
        collectFlowWhenStarted(recipeViewModel.searchIngredientList) {
            val resultList = it
            if (resultList.isEmpty()) {
                binding.emptySearch = getString(R.string.search_empty)
                binding.searching = true
            } else {
                detailAdapter.submitList(resultList)
                binding.searching = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recipeViewModel.clearSearchRecipe()
    }

}