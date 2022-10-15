package com.angdroid.refrigerator_manament.presentation.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
        Food("123", 107, LocalDate.now(), "계란", "", 3, 6),
        Food("123", 107, LocalDate.now(), "계란", "", 3, 1),
        Food("123", 107, LocalDate.now(), "계란", "", 3, 2),
        Food("123", 107, LocalDate.now(), "계란", "", 3, 6),
        Food("123", 103, LocalDate.now(), "무우", "", 1, 4),
        Food("123", 103, LocalDate.now(), "무우", "", 1, 1),
        Food("123", 103, LocalDate.now(), "무우", "", 1, 2),
        Food("123", 103, LocalDate.now(), "무우", "", 1, 3),
        Food("123", 104, LocalDate.now(), "사과", "", 2, 4),
        Food("123", 105, LocalDate.now(), "배", "", 2, 1),
        Food("123", 110, LocalDate.now(), "치즈", "", 4, 1),
        Food("123", 108, LocalDate.now(), "생닭", "", 3, 1),
        Food("123", 101, LocalDate.now(), "당근", "", 1, 4),
    )
    lateinit var adapter: CategoryListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        array.sortBy { it.category }
        /*val intenttt = Intent(this, MainActivity2::class.java)
        intenttt.putExtra("data", (array[1] as Food))
        startActivity(intenttt)
        finish()*/

        val result = array.groupingBy { it.id }
            .aggregate { _, accumulator: BaseType?, element, first ->
                if (first) element
                else {
                    (accumulator as Food).foodCount = accumulator.count + element.count
                    Log.e("Accumulator", accumulator.toString())
                    accumulator
                }
            }.values.toMutableList()
        result.sortBy { it.category }


        Log.e("Result!!", "$result")
        var categoryTemp: Pair<Int, Int> =
            Pair(result[result.size - 1].category, result[result.size - 1].count)
        for (i in result.size - 1 downTo 0) {
            result[i].run {
                if (this.category != categoryTemp.first) {
                    result.add(
                        i + 1,
                        Category(
                            resources.getString(categoryList[categoryTemp.first - 1]),
                            categoryTemp.second,
                            categoryTemp.first
                        )
                    )
                    categoryTemp = Pair(this.category, this.count)
                } else {
                    categoryTemp.second.plus(this.count)
                }
            }
        }
        result.add(
            0,
            Category(
                resources.getString(categoryList[categoryTemp.first - 1]),
                categoryTemp.second,
                categoryTemp.first
            )
        )
        adapter = CategoryListAdapter({}, {})
        binding.rvList.layoutManager = DynamicGridLayoutManager(this, adapter.getSpanSizeLookUp())
        binding.rvList.adapter = adapter
        adapter.submitList(result)
    }

    companion object CategoryType {
        val categoryList = listOf<Int>(
            R.string.category_vegetable, R.string.category_fruit,
            R.string.category_meat, R.string.category_seasoning
        )
    }
}