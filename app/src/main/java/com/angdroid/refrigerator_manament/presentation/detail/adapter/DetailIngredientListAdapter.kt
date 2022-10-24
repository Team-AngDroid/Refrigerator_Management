package com.angdroid.refrigerator_manament.presentation.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.window.layout.WindowMetrics
import com.angdroid.refrigerator_manament.BR
import com.angdroid.refrigerator_manament.databinding.ItemIngredientDetailBinding
import com.angdroid.refrigerator_manament.domain.entity.model.IngredientType
import com.angdroid.refrigerator_manament.presentation.util.GlobalDiffCallBack
import com.angdroid.refrigerator_manament.presentation.util.dpToPx

class DetailIngredientListAdapter(private val windowMetrics: WindowMetrics) :
    ListAdapter<IngredientType.Food, DetailIngredientListAdapter.IngredientViewHolder>(
        GlobalDiffCallBack()
    ) {

    private lateinit var inflater: LayoutInflater


    class IngredientViewHolder(val binding: ItemIngredientDetailBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        if (!::inflater.isInitialized) {
            inflater = LayoutInflater.from(parent.context)
        }
        return IngredientViewHolder(ItemIngredientDetailBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {

        (holder.binding.clRoot.layoutParams as ViewGroup.LayoutParams).apply {
            if (position == 0) {
                (this as MarginLayoutParams).marginStart =
                    16.dpToPx(context = holder.binding.clRoot.context)
            }
            width =
                (windowMetrics.bounds.width() - 44.dpToPx(holder.binding.clRoot.context)) / 2 // 양쪽 여백 + 사이 여백

        }
        holder.binding.setVariable(BR.food, getItem(position))
    }

}