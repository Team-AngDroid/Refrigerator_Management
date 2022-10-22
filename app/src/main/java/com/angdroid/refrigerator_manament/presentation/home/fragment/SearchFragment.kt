package com.angdroid.refrigerator_manament.presentation.home.fragment

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.databinding.FragmentSearchBinding
import com.angdroid.refrigerator_manament.presentation.home.adapter.SearchAdapter
import com.angdroid.refrigerator_manament.presentation.util.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val recipes
    =
        listOf("당근케이크", "카레", "당근 머핀", "오이소박이", "오이미역냉국", "오이 무침", "무생채", "고등어 무조림"
        ,"과일 사라다","사과 잼","사과 무상채 무침","배도라지대추차","배깍두기","사과배깍두기","귤잼","귤 그라니따","귤 머핀"
        ,"계란 장조림","계란말이","에어프라이어 치킨","닭볶음탕","닭가슴오이냉채","우유 푸딩","딸기 우유","에그치즈토스트","체다치즈칩스"
        ,"새우장","칠리새우","브로콜리 새우 볶음","오징어 볶음","오징어 찌개","고등어 구이","고등어 김치 조림","고등어 김치찜"
        ) //TODO 띄어쓰기 같은거 통일 필요할듯
    private lateinit var autoAdapter: ArrayAdapter<String>
    private lateinit var searchAdapter: SearchAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchAdapter = SearchAdapter(requireContext()){}
        binding.rcvSearch.adapter = searchAdapter
        searchAdapter.submitList(listOf("레시피 검색을 해보세요\uD83D\uDE0B"))

        autoAdapter = ArrayAdapter<String>(requireContext(), R.layout.spinner_list, recipes)
        binding.autoSearch.setAdapter(autoAdapter)



        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.layoutEtSearch.setStartIconOnClickListener {
            val resultList = recipes.filter { it.contains(binding.autoSearch.text.toString())}
            if(resultList.isEmpty())
                searchAdapter.submitList(listOf("검색결과가 없습니다."))
            else
                searchAdapter.submitList(resultList)
        }


    }

}