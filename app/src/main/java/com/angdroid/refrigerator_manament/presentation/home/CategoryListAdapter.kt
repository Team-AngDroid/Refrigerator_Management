package com.angdroid.refrigerator_manament.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.angdroid.refrigerator_manament.BR
import com.angdroid.refrigerator_manament.databinding.ItemCategoryListBinding
import com.angdroid.refrigerator_manament.presentation.home.model.Food

class CategoryListAdapter(
    private val itemClickListener: (Food) -> Unit,
    private val itemRemoveListener: (Food) -> Unit
) : ListAdapter<Food, CategoryListAdapter.CategoryViewHolder>(FoodListDiffCallBack) {

    class CategoryViewHolder(val binding: ItemCategoryListBinding) : ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder =
        CategoryViewHolder(
            ItemCategoryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.binding.setVariable(BR.food, currentItem)
        holder.binding.root.setOnClickListener { itemClickListener(currentItem) }
        holder.binding.ivRemove.setOnClickListener { itemRemoveListener(currentItem) }
    }

    companion object FoodListDiffCallBack : DiffUtil.ItemCallback<Food>() {
        override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean = oldItem == newItem
    }
}