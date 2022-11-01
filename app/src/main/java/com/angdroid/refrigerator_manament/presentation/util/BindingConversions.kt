package com.angdroid.refrigerator_manament.presentation.util

import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.RoundedCornersTransformation
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.application.App
import com.angdroid.refrigerator_manament.presentation.util.types.FoodTypeFeatures
import com.angdroid.refrigerator_manament.domain.util.CategoryType
import java.time.LocalDate


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

// 레시피 검색당시 색깔없이 보여주기 위해 추가한 바인딩 어댑터 현재 사용처 없음
@BindingAdapter("app:search_ingredients")
fun TextView.searchIngredient(ingredients: List<String>) {
    if (App.getUserIngredientInfoInitialized()) {
        App.userIngredientInfo.run {
            val comparator: Comparator<String> =
                compareByDescending {
                    this.contains(it)
                }
            val sortedIngredients = ingredients.sortedWith(comparator)

            val builder =
                SpannableStringBuilder(sortedIngredients.joinToString("·"))
            text = builder
        }
    }
}


@BindingAdapter("foodName", "foodCount")
fun spanColorCount(textView: TextView, foodName: String, foodCount: Int) {
    val builder = SpannableStringBuilder(
        textView.resources.getString(
            R.string.ingredient,
            foodName,
            foodCount
        )
    )
    val span =
        ForegroundColorSpan(Color.parseColor(textView.resources.getString(R.string.already_have_ingredient)))
    builder.indexOf(foodCount.toString()).run {
        if (this > -1) {
            builder.setSpan(
                span,
                this,
                this + foodCount.toString().length,
                Spanned.SPAN_EXCLUSIVE_INCLUSIVE
            )
        }
    }
    textView.text = builder
}

@BindingAdapter("app:setExpirationDate")
fun TextView.setExpirationDate(expirationDate: LocalDate) {
    val now = LocalDate.now()

    if (expirationDate.year == now.year) {
        text =
            resources.getString(R.string.until_day, (expirationDate.dayOfYear - now.dayOfYear))
        if ((expirationDate.dayOfYear - now.dayOfYear) <= 3) {
            setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_warning, 0)
            compoundDrawablePadding = 4.dpToPx(context)
            setTextColor(Color.parseColor(resources.getString(R.color.warn_red)))
        }
    } else {
        text = resources.getString(
            R.string.until_day,
            (365 - now.dayOfYear) + expirationDate.dayOfYear
        )
        if ((365 - now.dayOfYear) + expirationDate.dayOfYear <= 3) {
            setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_warning, 0)
            compoundDrawablePadding = 4.dpToPx(context)
            setTextColor(Color.parseColor(resources.getString(R.color.warn_red)))
        }

    }
}

@BindingAdapter("app:count_btn_tint")
fun ImageView.setImageTint(@ColorInt color: Int) {
    setColorFilter(color)
}