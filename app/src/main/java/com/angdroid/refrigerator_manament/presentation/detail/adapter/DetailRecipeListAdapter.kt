package com.angdroid.refrigerator_manament.presentation.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.angdroid.refrigerator_manament.BR
import com.angdroid.refrigerator_manament.databinding.ItemRecipeDetailBinding
import com.angdroid.refrigerator_manament.domain.entity.RecipeEntity
import com.angdroid.refrigerator_manament.presentation.util.dpToPx

class DetailRecipeListAdapter(
) :
    ListAdapter<RecipeEntity, DetailRecipeListAdapter.DetailViewHolder>(DetailDiffCallBack) {
    private lateinit var inflater: LayoutInflater

    class DetailViewHolder(val binding: ItemRecipeDetailBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        if (!::inflater.isInitialized) {
            inflater = LayoutInflater.from(parent.context)
        }
        return DetailViewHolder(
            ItemRecipeDetailBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        (holder.binding.clCategory.layoutParams as ViewGroup.MarginLayoutParams).apply {
            if (position == itemCount - 1) {
                bottomMargin = 32.dpToPx(context = holder.binding.clCategory.context)
            }
        }
        holder.binding.setVariable(BR.recipeItem, getItem(position))
    }

    companion object {
        private object DetailDiffCallBack : DiffUtil.ItemCallback<RecipeEntity>() {
            override fun areItemsTheSame(
                oldItem: RecipeEntity,
                newItem: RecipeEntity
            ): Boolean {
                return oldItem.id == newItem.id
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