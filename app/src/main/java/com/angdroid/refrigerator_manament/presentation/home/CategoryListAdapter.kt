package com.angdroid.refrigerator_manament.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.angdroid.refrigerator_manament.BR
import com.angdroid.refrigerator_manament.databinding.ItemCategoryListBinding
import com.angdroid.refrigerator_manament.databinding.ItemCategoryTitleBinding
import com.angdroid.refrigerator_manament.presentation.home.model.IngredientType
import com.angdroid.refrigerator_manament.presentation.util.dpToPx

class CategoryListAdapter(
    private val itemClickListener: (IngredientType.Food) -> Unit,
    private val itemRemoveListener: (IngredientType.Food) -> Unit
) : ListAdapter<IngredientType, RecyclerView.ViewHolder>(FoodListDiffCallBack) {
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
                    binding.setVariable(BR.food, (currentItem as IngredientType.Food))
                    binding.root.setOnClickListener { itemClickListener(currentItem) }
                    binding.ivRemove.setOnClickListener { itemRemoveListener(currentItem) }
                    if (position == itemCount - 1) {
                        binding.clFood.layoutParams =
                            (binding.clFood.layoutParams as ViewGroup.MarginLayoutParams).apply {
                                bottomMargin = 24.dpToPx(binding.root.context)
                            }
                    }
                }
            }
            Companion.CATEGORY -> {
                with(holder as CategoryTitleViewHolder) {
                    binding.setVariable(BR.titleModel, (currentItem as IngredientType.Category))


                }
            }

        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.type == 0) ITEM else CATEGORY
    }

    fun getSpanSizeLookUp(): GridLayoutManager.SpanSizeLookup {
        return object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (getItemViewType(position) == 2) 1 else 3
            }
        }
    }


    class CategoryViewHolder(val binding: ItemCategoryListBinding) :
        RecyclerView.ViewHolder(binding.root)

    class CategoryTitleViewHolder(val binding: ItemCategoryTitleBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        const val CATEGORY = 1
        const val ITEM = 2

        private object FoodListDiffCallBack : DiffUtil.ItemCallback<IngredientType>() {
            override fun areItemsTheSame(
                oldItem: IngredientType,
                newItem: IngredientType
            ): Boolean {
                return oldItem.id == 0 && newItem.id == 0
            }

            override fun areContentsTheSame(
                oldItem: IngredientType,
                newItem: IngredientType
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}