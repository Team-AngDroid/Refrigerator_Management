package com.angdroid.refrigerator_manament.presentation.camera

import android.os.Bundle
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.databinding.ActivityAddIngredientBinding
import com.angdroid.refrigerator_manament.presentation.camera.adapter.AddIngredientAdapter
import com.angdroid.refrigerator_manament.presentation.util.BaseActivity

class AddIngredientActivity :
    BaseActivity<ActivityAddIngredientBinding>(R.layout.activity_add_ingredient) {

    private lateinit var adapter: AddIngredientAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAppbar()
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


}