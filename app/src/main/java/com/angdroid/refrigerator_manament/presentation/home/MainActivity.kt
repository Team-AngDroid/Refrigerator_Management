package com.angdroid.refrigerator_manament.presentation.home

import android.os.Bundle
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.databinding.ActivityMainBinding
import com.angdroid.refrigerator_manament.presentation.DynamicGridLayoutManager
import com.angdroid.refrigerator_manament.presentation.home.model.BaseType
import com.angdroid.refrigerator_manament.presentation.home.model.Category
import com.angdroid.refrigerator_manament.presentation.home.model.Food
import com.angdroid.refrigerator_manament.presentation.util.BaseActivity
import java.time.LocalDate

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val array = arrayListOf<BaseType>(
        Food("123", 7, LocalDate.now(), "계란", "", 3, 1),
        Food("123", 9, LocalDate.now(), "우유", "", 4, 1),
        Food("123", 1, LocalDate.now(), "당근", "", 1, 4),
        Food("123", 3, LocalDate.now(), "무우", "", 1, 4),
        Food("123", 4, LocalDate.now(), "사과", "", 2, 4),
        Food("123", 5, LocalDate.now(), "배", "", 2, 1),
        Food("123", 10, LocalDate.now(), "치즈", "", 4, 1),
        Food("123", 8, LocalDate.now(), "생닭", "", 3, 1),
        Food("123", 1, LocalDate.now(), "당근", "", 1, 4),
    )
    lateinit var adapter: CategoryListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        array.sortBy { it.getCategoryId() }
        var categoryTemp: Pair<Int, Int> =
            Pair(array[array.size - 1].getCategoryId(), array[array.size - 1].getCount())
        for (i in array.size - 1 downTo 0) {
            array[i].run {
                if (this.getCategoryId() != categoryTemp.first) {
                    array.add(i + 1, Category("유제품", categoryTemp.second, categoryTemp.first))
                    categoryTemp = Pair(this.getCategoryId(), this.getCount())
                } else {
                    categoryTemp.second.plus(this.getCount())
                }
            }
        }
        array.add(0, Category("채소", categoryTemp.second, categoryTemp.first))
        adapter = CategoryListAdapter({}, {})
        binding.rvList.layoutManager = DynamicGridLayoutManager(this, adapter.GridSpanSizeLookup())
        binding.rvList.adapter = adapter
        adapter.submitList(array)
    }
}