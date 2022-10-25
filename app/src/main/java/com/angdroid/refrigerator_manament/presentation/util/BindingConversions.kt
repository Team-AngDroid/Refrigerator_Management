package com.angdroid.refrigerator_manament.presentation.util

import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.RoundedCornersTransformation
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.application.App
import com.angdroid.refrigerator_manament.util.CategoryType
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

@BindingAdapter("app:necessary_ingredients")
fun TextView.necessaryIngredients(ingredients: List<String>) {
    if (App.getUserIngredientInfoInitialized()) {
        App.userIngredientInfo.run {
            val comparator: Comparator<String> =
                compareByDescending {
                    this.contains(it)
                }
            val sortedIngredients = ingredients.sortedWith(comparator)

            val builder =
                SpannableStringBuilder(sortedIngredients.joinToString("·"))
            this.forEach { ingredient ->
                val startIndex = builder.indexOf(ingredient)
                if (startIndex > -1) {
                    val span =
                        ForegroundColorSpan(Color.parseColor(resources.getString(R.string.already_have_ingredient)))
                    builder.setSpan(
                        span,
                        startIndex,
                        startIndex + ingredient.length,
                        Spanned.SPAN_EXCLUSIVE_INCLUSIVE
                    )
                }
            }
            text = builder
        }
    }
}