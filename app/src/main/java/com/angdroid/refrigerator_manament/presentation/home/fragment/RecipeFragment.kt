package com.angdroid.refrigerator_manament.presentation.home.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.databinding.FragmentRecipeBinding
import com.angdroid.refrigerator_manament.domain.entity.RecipeEntity
import com.angdroid.refrigerator_manament.presentation.home.RecipeViewmodel
import com.angdroid.refrigerator_manament.presentation.home.adapter.RecipeTitleAdapter
import com.angdroid.refrigerator_manament.presentation.util.BaseFragment
import com.angdroid.refrigerator_manament.util.collectFlowWhenStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers

@AndroidEntryPoint
class RecipeFragment:
    BaseFragment<FragmentRecipeBinding>(R.layout.fragment_recipe) {

    private val recipeViewModel: RecipeViewmodel by viewModels()
    private val recipeList = listOf<RecipeEntity>()
    private lateinit var recipeAdapter: RecipeTitleAdapter
    private lateinit var recipeAdapter2: RecipeTitleAdapter

    private val hardRecipeList = listOf<RecipeEntity>(
        RecipeEntity("", "당큰케이크", "20분","","당근", listOf()),
        RecipeEntity("", "과일 사라다", "30분","","사과", listOf()),
        RecipeEntity("", "체다슬라이스칩스", "40분","","치즈", listOf()),
        RecipeEntity("", "무생채", "60분","","무우", listOf()),
        RecipeEntity("", "에어프라이어치킨", "30분","","생닭", listOf()),
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //recipeViewModel.getAllRecipe()
        Dispatchers.Default
        collectData()
        setAdapter()
        binding.etSearch.setOnClickListener {
            findNavController().navigate(R.id.action_recipeFragment_to_searchFragment)
        }
    }

    private fun setAdapter(){
        recipeAdapter = RecipeTitleAdapter(requireContext(),{})

        recipeAdapter.submitList(listOf(
            RecipeEntity("", "치즈", "20분","","당근", listOf()),
            RecipeEntity("1", "체다슬라이스칩스", "40분","","https://recipe1.ezmember.co.kr/cache/recipe/2019/01/17/117b05ae5e777da4c59e60d88da715ad1.jpg", listOf("치즈")),
            RecipeEntity("1", "치즈에그토스트", "40분","","https://recipe1.ezmember.co.kr/cache/recipe/2020/08/18/32775e06923a4bef0cb6093ff84d28ef1.jpg", listOf("치즈","식빵","계란","소금","버터")),
            RecipeEntity("", "당근", "20분","","당근", listOf()),
            RecipeEntity("1", "당근 머핀", "30분","","https://recipe1.ezmember.co.kr/cache/recipe/2016/05/09/31a2118d4c1190d6c7a66c8eba5fdfbd1.jpg", listOf("당근","계란","베이킹파우더","박력분","설탕")),
            RecipeEntity("1", "당큰케이크", "20분","","https://recipe1.ezmember.co.kr/cache/recipe/2015/05/13/754f94e0c7e2fa1f4c11f7c0ff36d0071.jpg", listOf("당근","계란","오일","베이킹파우더","박력분")),
            RecipeEntity("", "귤", "20분","","귤", listOf()),
            RecipeEntity("1", "귤잼", "90분","","https://recipe1.ezmember.co.kr/cache/recipe/2016/02/19/30fbc1ccaa8b1b728dc653cca450a87a1.jpg", listOf("귤","설탕")),
            RecipeEntity("1", "귤 그라니따", "120분","","https://recipe1.ezmember.co.kr/cache/recipe/2017/11/12/7acbaef34eff913dcdeb3db252dab30a1.jpg", listOf("귤","꿀","감귤주스","민트")),
            RecipeEntity("", "계란", "20분","","계란", listOf()),
            RecipeEntity("1", "계란말이", "15분","","https://recipe1.ezmember.co.kr/cache/recipe/2015/11/12/006d3080be97cb3328a20931e7eafddc1.jpg", listOf("계란","대파","물","소금")),
            RecipeEntity("1", "계란장조림", "30분","","https://recipe1.ezmember.co.kr/cache/recipe/2019/02/06/0c852122b2b74b76bf78cc0c1527a0e21.jpg", listOf("계란","간장","통마늘","소금"))
        ))
        binding.rcvRecipe.adapter = recipeAdapter
//        recipeAdapter = RecipeTitleAdapter(requireContext(),{})
//        recipeAdapter2 = RecipeTitleAdapter(requireContext(),{})
//
//
//        recipeAdapter.submitList(listOf(
//            RecipeEntity("", "치즈", "20분","","당근", listOf()),
//            RecipeEntity("1", "체다슬라이스칩스", "40분","","https://recipe1.ezmember.co.kr/cache/recipe/2019/01/17/117b05ae5e777da4c59e60d88da715ad1.jpg", listOf()),
//            RecipeEntity("1", "치즈에그토스트", "40분","","https://recipe1.ezmember.co.kr/cache/recipe/2020/08/18/32775e06923a4bef0cb6093ff84d28ef1.jpg", listOf()),
//        ))
//        recipeAdapter2.submitList(listOf(
//            RecipeEntity("", "당큰", "20분","","당근", listOf()),
//            RecipeEntity("1", "당근 머핀", "30분","","https://recipe1.ezmember.co.kr/cache/recipe/2016/05/09/31a2118d4c1190d6c7a66c8eba5fdfbd1.jpg", listOf()),
//            RecipeEntity("1", "당큰케이크", "20분","","https://recipe1.ezmember.co.kr/cache/recipe/2015/05/13/754f94e0c7e2fa1f4c11f7c0ff36d0071.jpg", listOf())
//
//        ))
//
//        val concact = ConcatAdapter(recipeAdapter,recipeAdapter2)
//        binding.rcvRecipe.adapter = concact
//concat 잘되는데 adapter2가 보여지는 순간 에러남
    }

    private fun collectData() {

        val set = mutableSetOf<Int>()
        while (set.size < 2){
            set.add((hardRecipeList.indices).random())
        }
        binding.recommend1 = hardRecipeList[set.elementAt(0)]
        binding.recommend2 = hardRecipeList[set.elementAt(1)]

        collectFlowWhenStarted(recipeViewModel.recipeList) {} // DetailActivity랑 동일하게 해봤는데 에러남.. 나중에 해결
    }


}
