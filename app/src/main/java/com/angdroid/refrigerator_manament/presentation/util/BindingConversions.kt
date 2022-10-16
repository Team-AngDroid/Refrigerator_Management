package com.angdroid.refrigerator_manament.presentation.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.RoundedCornersTransformation
import com.angdroid.refrigerator_manament.presentation.util.types.CategoryType
import com.angdroid.refrigerator_manament.presentation.util.types.FoodTypeFeatures


@BindingAdapter("app:load_remote_coil_corner")
fun ImageView.loadRemoteCoilCorner(url: String) {

    if (url.isNotEmpty()) {
        this.load(url) {
            transformations(RoundedCornersTransformation(12.0F))
        }
    }
}


@BindingAdapter("app:load_default_ingredient")
fun ImageView.loadDefaultIngredient(foodName: String) {
    this.setImageResource(FoodTypeFeatures.valueOf(foodName).imageRes)
}

@BindingAdapter("app:category_text")
fun TextView.categoryText(categoryId: Int) {
    text = resources.getText(CategoryType.categoryIdList[categoryId - 1])
}