package com.angdroid.refrigerator_manament.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.angdroid.refrigerator_manament.BR
import com.angdroid.refrigerator_manament.data.dto.CategoryType
import com.angdroid.refrigerator_manament.databinding.ItemCategoryListBinding
import com.angdroid.refrigerator_manament.presentation.home.model.Food

class CategoryListAdapter(
    private val itemClickListener: (Food) -> Unit,
    private val itemRemoveListener: (Food) -> Unit
) : ListAdapter<Food, RecyclerView.ViewHolder>(FoodListDiffCallBack) {

    var currentCategoryType: CategoryType? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CategoryViewHolder(
            ItemCategoryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        when (holder.itemViewType) {
            Companion.ITEM -> {
                (holder as CategoryViewHolder).apply {
                    binding.setVariable(BR.food, currentItem)
                    binding.root.setOnClickListener { itemClickListener(currentItem) }
                    binding.ivRemove.setOnClickListener { itemRemoveListener(currentItem) }
                }
            }

        }
    }

    override fun getItemViewType(position: Int): Int {

        return super.getItemViewType(position)
    }

    companion object {

        private const val CATEGORY = 1
        private const val ITEM = 2

        private object FoodListDiffCallBack : DiffUtil.ItemCallback<Food>() {
            override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean =
                oldItem == newItem
        }
    }

    class CategoryViewHolder(val binding: ItemCategoryListBinding) :
        RecyclerView.ViewHolder(binding.root)
}
