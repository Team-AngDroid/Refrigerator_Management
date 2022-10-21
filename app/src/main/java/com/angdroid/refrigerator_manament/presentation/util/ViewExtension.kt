package com.angdroid.refrigerator_manament.presentation.util

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.google.android.material.snackbar.Snackbar

fun View.makeSnackbar(messgae: String) {
    Snackbar.make(
        this,
        messgae, Snackbar.LENGTH_SHORT
    ).show()
}


fun View.setOnSingleClickListener(onSingleClick: (View) -> Unit) {
    setOnClickListener(OnSingleClickListener { onSingleClick(it) })
}

fun Int.dpToPx(context: Context): Int {
    return context.resources.displayMetrics.density.let { density ->
        (this * density).toInt()
    }
}

fun Int.pxToDp(context: Context): Int {
    return context.resources.displayMetrics.density.let { density ->
        (this / density).toInt()
    }
}

fun ListAdapter<*, *>.getSpanSizeLookUp(): GridLayoutManager.SpanSizeLookup {
    return object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return if (getItemViewType(position) == 2) 1 else 3
        }
    }
}