package com.angdroid.refrigerator_manament.presentation.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.angdroid.refrigerator_manament.BR
import com.angdroid.refrigerator_manament.databinding.ItemRecipeDetailBinding
import com.angdroid.refrigerator_manament.databinding.ItemRecipeTitleBinding
import com.angdroid.refrigerator_manament.domain.entity.RecipeEntity

class RecipeAdapter(
    context: Context, private val itemClickListener: (RecipeEntity) -> Unit,
) : ListAdapter<RecipeEntity, RecyclerView.ViewHolder>(RecipeListDiffCallBack) {

    private val inflater by lazy { LayoutInflater.from(context) }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TITLE -> {
                RecipeTitleViewHolder(
                    ItemRecipeTitleBinding.inflate(inflater, parent, false)
                )
            }
            else -> {
                RecipeDetailViewHolder(
                    ItemRecipeDetailBinding.inflate(inflater, parent, false)
                )
            }
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = getItem(position)

        when (holder.itemViewType) {
            RECIPE -> {
                with(holder as RecipeDetailViewHolder) {
                    holder.binding.setVariable(BR.recipeItem, getItem(position))
                    binding.root.setOnClickListener { itemClickListener(currentItem) }
                }
            }
            TITLE -> {
                with(holder as RecipeTitleViewHolder) {
                    holder.binding.setVariable(BR.ingredient, getItem(position))
                }
            }

        }
    }


    class RecipeTitleViewHolder(val binding: ItemRecipeTitleBinding) :
        RecyclerView.ViewHolder(binding.root)

    class RecipeDetailViewHolder(val binding: ItemRecipeDetailBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).id == "") TITLE else RECIPE
    }

    companion object {

        const val TITLE = 0
        const val RECIPE = 1

        private object RecipeListDiffCallBack : DiffUtil.ItemCallback<RecipeEntity>() {
            override fun areItemsTheSame(
                oldItem: RecipeEntity,
                newItem: RecipeEntity
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: RecipeEntity,
                newItem: RecipeEntity
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}