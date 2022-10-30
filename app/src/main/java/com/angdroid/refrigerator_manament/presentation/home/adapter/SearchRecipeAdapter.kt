package com.angdroid.refrigerator_manament.presentation.home.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.angdroid.refrigerator_manament.BR
import com.angdroid.refrigerator_manament.databinding.ItemSearchRecipeBinding
import com.angdroid.refrigerator_manament.domain.entity.RecipeEntity

class SearchRecipeAdapter :
    ListAdapter<RecipeEntity, SearchRecipeAdapter.DetailViewHolder>(DetailDiffCallBack) {
    private lateinit var inflater: LayoutInflater

    class DetailViewHolder(val binding: ItemSearchRecipeBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        if (!::inflater.isInitialized) {
            inflater = LayoutInflater.from(parent.context)
        }
        return DetailViewHolder(
            ItemSearchRecipeBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.binding.setVariable(BR.recipeItem, getItem(position))
    }

    companion object {
        private object DetailDiffCallBack : DiffUtil.ItemCallback<RecipeEntity>() {
            override fun areItemsTheSame(
                oldItem: RecipeEntity,
                newItem: RecipeEntity
            ): Boolean {
                return false
            }

            override fun areContentsTheSame(
                oldItem: RecipeEntity,
                newItem: RecipeEntity
            ): Boolean {
                return false
            }
        }
    }
}