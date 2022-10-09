package com.angdroid.refrigerator_manament.presentation.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.presentation.home.model.BaseType
import com.angdroid.refrigerator_manament.presentation.home.model.Category
import com.angdroid.refrigerator_manament.presentation.home.model.Food
import java.time.LocalDate

class MainActivity : AppCompatActivity() {
    private val array = ArrayList<BaseType>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        array.add(Category("채소", 2, 1))
        array.add(Food(1, LocalDate.now(), "", "", 1, 4))
    }
}