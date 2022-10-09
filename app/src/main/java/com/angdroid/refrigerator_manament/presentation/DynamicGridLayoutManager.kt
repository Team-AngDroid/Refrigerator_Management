package com.angdroid.refrigerator_manament.presentation

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager

class DynamicGridLayoutManager(context: Context, spanSize: SpanSizeLookup) :
    GridLayoutManager(context, 3) {

    init {
        spanSizeLookup = spanSize
    }
}