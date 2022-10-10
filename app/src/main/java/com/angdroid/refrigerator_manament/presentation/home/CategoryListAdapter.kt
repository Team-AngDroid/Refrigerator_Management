package com.angdroid.refrigerator_manament.presentation.home

import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.angdroid.refrigerator_manament.BR
import com.angdroid.refrigerator_manament.data.dto.CategoryType
import com.angdroid.refrigerator_manament.databinding.ItemCategoryListBinding
import com.angdroid.refrigerator_manament.databinding.ItemCategoryTitleBinding
import com.angdroid.refrigerator_manament.presentation.home.model.BaseType
import com.angdroid.refrigerator_manament.presentation.home.model.Category
import com.angdroid.refrigerator_manament.presentation.home.model.Food

class CategoryListAdapter(
    private val itemClickListener: (Food) -> Unit,
    private val itemRemoveListener: (Food) -> Unit
) : ListAdapter<BaseType, RecyclerView.ViewHolder>(FoodListDiffCallBack) {

    private lateinit var inflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (!::inflater.isInitialized) {
            inflater = LayoutInflater.from(parent.context)
        }
        return when (viewType) {
            Companion.ITEM -> {
                CategoryViewHolder(
                    ItemCategoryListBinding.inflate(inflater, parent, false)
                )
            }
            else -> {
                CategoryTitleViewHolder(
                    ItemCategoryTitleBinding.inflate(inflater, parent, false)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        when (holder.itemViewType) {
            Companion.ITEM -> {
                with(holder as CategoryViewHolder) {
                    binding.setVariable(BR.food, (currentItem as Food))
                    binding.root.setOnClickListener { itemClickListener(currentItem as Food) }
                    binding.ivRemove.setOnClickListener { itemRemoveListener(currentItem as Food) }
                }
            }
            Companion.CATEGORY -> {
                with(holder as CategoryTitleViewHolder) {
                    binding.setVariable(BR.titleModel, (currentItem as Category))
                }
            }

        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.getType() == 0) ITEM else CATEGORY
    }

    inner class GridSpanSizeLookup : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return if (getItemViewType(position) == 2) 1 else 3
        }
    }

    companion object {
        const val CATEGORY = 1
        const val ITEM = 2

        private object FoodListDiffCallBack : DiffUtil.ItemCallback<BaseType>() {
            override fun areItemsTheSame(oldItem: BaseType, newItem: BaseType): Boolean {
                return if (oldItem.getType() == 0 && newItem.getType() == 0) {
                    (oldItem as Food).id == (newItem as Food).id
                } else {
                    oldItem.getType() == newItem.getType()
                }
            }

            override fun areContentsTheSame(oldItem: BaseType, newItem: BaseType): Boolean =
                oldItem.hashCode() == newItem.hashCode()
        }
    }

    class CategoryViewHolder(val binding: ItemCategoryListBinding) :
        RecyclerView.ViewHolder(binding.root)

    class CategoryTitleViewHolder(val binding: ItemCategoryTitleBinding) :
        RecyclerView.ViewHolder(binding.root)
}
