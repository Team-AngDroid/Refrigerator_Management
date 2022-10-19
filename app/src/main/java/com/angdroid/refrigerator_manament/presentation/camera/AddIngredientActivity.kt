package com.angdroid.refrigerator_manament.presentation.camera

import android.os.Bundle
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.databinding.ActivityAddIngredientBinding
import com.angdroid.refrigerator_manament.domain.entity.model.IngredientType
import com.angdroid.refrigerator_manament.presentation.camera.adapter.AddIngredientAdapter
import com.angdroid.refrigerator_manament.presentation.util.BaseActivity
import java.time.LocalDate

class AddIngredientActivity :
    BaseActivity<ActivityAddIngredientBinding>(R.layout.activity_add_ingredient) {

    private lateinit var adapter: AddIngredientAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAppbar()
        initAdapter()

    }

    private fun setAppbar() {
        binding.appbarIngredient.topAppbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.top_back -> {
                    finish()
                    true
                }
                else -> false
            }
        }
    }

    private fun initAdapter(){
        adapter = AddIngredientAdapter(this)
        binding.rcvIngredients.adapter = adapter
        adapter.submitList(getIngredients().toList())
    }

    private fun getIngredients(): ArrayList<IngredientType.Food> {
        val ingredientsList =
            intent.getParcelableArrayListExtra<IngredientType.Food>("Ingredients")!!
        ingredientsList.add(
            IngredientType.Food("0", 0, LocalDate.now(), "", "", 0, 0)
            // 마지막 [직접 추가]아이템용 더미데이터
        )
        return ingredientsList
    }


}