package com.angdroid.refrigerator_manament.presentation.util.types

import androidx.annotation.DrawableRes
import com.angdroid.refrigerator_manament.R


enum class FoodTypeFeatures(@DrawableRes val imageRes: Int) {
    당근(R.drawable.carrot), 오이(R.drawable.cucumber), 무우(R.drawable.moo),
    사과(R.drawable.apple), 배(R.drawable.pear), 귤(R.drawable.tangerine),
    계란(R.drawable.egg), 생닭(R.drawable.chicken), 우유(R.drawable.milk),
    치즈(R.drawable.cheese), 새우(R.drawable.shrimp), 오징어(R.drawable.squid), 고등어(R.drawable.mackerel),
}