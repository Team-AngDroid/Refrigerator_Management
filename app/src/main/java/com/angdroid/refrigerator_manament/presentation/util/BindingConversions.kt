package com.angdroid.refrigerator_manament.presentation.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.RoundedCornersTransformation
import com.angdroid.refrigerator_manament.R


@BindingAdapter("app:load_remote_coil_corner")
fun ImageView.loadRemoteCoilCorner(url: String) {

    if (url.isNotEmpty()) {
        this.load(url) {
            transformations(RoundedCornersTransformation(12.0F))
        }
    } else {
        this.load(R.drawable.temp1)
    }
}


@BindingAdapter("app:load_default_ingredient")
fun ImageView.loadDefaultIngredient(foodName:String){
    this.setImageResource(FoodTypeFeatures.valueOf(foodName).imageRes)
}