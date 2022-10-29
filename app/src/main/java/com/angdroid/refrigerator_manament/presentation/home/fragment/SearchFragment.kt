package com.angdroid.refrigerator_manament.presentation.home.fragment

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.SimpleItemAnimator
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.databinding.FragmentSearchBinding
import com.angdroid.refrigerator_manament.presentation.detail.adapter.DetailListAdapter
import com.angdroid.refrigerator_manament.presentation.home.RecipeViewModel
import com.angdroid.refrigerator_manament.presentation.home.adapter.SearchAdapter
import com.angdroid.refrigerator_manament.presentation.home.adapter.SearchRecipeAdapter
import com.angdroid.refrigerator_manament.presentation.util.BaseFragment
import com.angdroid.refrigerator_manament.util.collectFlowWhenStarted
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private lateinit var autoAdapter: ArrayAdapter<String>
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var detailAdapter: SearchRecipeAdapter
    private val recipeViewModel: RecipeViewModel by activityViewModels()


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
    }

    private fun setAdapters() {
        // 두개의 리싸이클러뷰를 넣어준 이유 -> 멀티뷰홀더 어댑터를 쓰기에는 Entity상 적합하지 않은거 같아서
        // 하나의 리싸이클러뷰에서 어댑터를 교체해주는 작업을 했으나 PR의 스크린샷 처럼 Blinking이 지속적으로 발생
        // 따라서 데이터바인딩을 통해서 두개의 RecyclerView의 visibility를 조절하는 방식을 사용
        searchAdapter = SearchAdapter(requireContext()) {}
        detailAdapter = SearchRecipeAdapter()
        binding.rcvEmpty.adapter = searchAdapter
        binding.rcvSearch.adapter = detailAdapter
        binding.searching = true
        searchAdapter.submitList(listOf("레시피를 검색해보세요\uD83D\uDE0B")) //초기 설정

        recipeViewModel.getRecipeNameList()
        collectFlowWhenStarted(recipeViewModel.recipeNameList) {
            autoAdapter = ArrayAdapter<String>(requireContext(), R.layout.spinner_list, it)
            binding.autoSearch.setAdapter(autoAdapter)
        }
    }

    private fun setListeners() {
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.layoutEtSearch.setStartIconOnClickListener {
            searchRecipe(binding.autoSearch.text.toString())
        }

        binding.autoSearch.setOnKeyListener { _, keyCode, event -> // enter키 이벤트
            if ((event.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                searchRecipe(binding.autoSearch.text.toString())
                true
            } else {
                false
            }
        }

    }

    private fun searchRecipe(search:String) { // 검색함수
        if (search != "") {
            recipeViewModel.getIngredientSearchRecipe(search)
            collectFlowWhenStarted(recipeViewModel.searchIngredientList) {
                val resultList = it
                if (resultList.isEmpty()) {
                    searchAdapter.submitList(listOf("검색결과가 없습니다."))
                    binding.searching = true
                } else {
                    detailAdapter.submitList(resultList)
                    binding.searching = false
                }
                binding.autoSearch.text = null
            }
            val inputMethodManager =
                requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(binding.autoSearch.windowToken, 0) //키보드 내리기
        }
    }

}