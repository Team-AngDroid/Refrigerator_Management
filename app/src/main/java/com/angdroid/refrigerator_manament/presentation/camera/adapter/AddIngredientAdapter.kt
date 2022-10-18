package com.angdroid.refrigerator_manament.presentation.camera.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.angdroid.refrigerator_manament.BR
import com.angdroid.refrigerator_manament.databinding.ItemIngredientsBinding
import com.angdroid.refrigerator_manament.databinding.ItemSelfIngredientsBinding
import com.angdroid.refrigerator_manament.presentation.home.model.IngredientType

class AddIngredientAdapter(
    context: Context,
    private val itemClickListener: (IngredientType.Food) -> Unit,
    private val itemRemoveListener: (IngredientType.Food) -> Unit,
    private val itemAddListener: (IngredientType.Food) -> Unit
) : ListAdapter<IngredientType, RecyclerView.ViewHolder>(IngredientDiffCallBack) {
    private val inflater by lazy { LayoutInflater.from(context) }

    class IngredientViewHolder(val binding: ItemIngredientsBinding) :
        RecyclerView.ViewHolder(binding.root)

    class SelfAddViewHolder(val binding: ItemSelfIngredientsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            INGREDIENTS -> {
                IngredientViewHolder(
                    ItemIngredientsBinding.inflate(inflater, parent, false)
                )
            }
            else -> {
                SelfAddViewHolder(
                    ItemSelfIngredientsBinding.inflate(inflater, parent, false)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = getItem(position)
        when (holder.itemViewType) {
            INGREDIENTS -> {
                with(holder as IngredientViewHolder) {
                    binding.setVariable(BR.food, (currentItem as IngredientType.Food))
                    binding.root.setOnClickListener { itemClickListener(currentItem) }
                    binding.ivDelete.setOnClickListener { itemRemoveListener(currentItem) }
                }
            }
            SELF -> {
                with(holder as SelfAddViewHolder) {
                    binding.clFood.setOnClickListener { itemAddListener }
                }
            }

        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == currentList.size - 1) SELF else INGREDIENTS
        //마지막 아이템의 경우 무조건 직접 추가 아이템이 나오도록
        // 하지만 이 경우 마지막에 프론트단에서 무조건 더미데이터 하나를 넣어주어야 함..
    }

    companion object {
        const val INGREDIENTS = 1
        const val SELF = 2

        private object IngredientDiffCallBack : DiffUtil.ItemCallback<IngredientType>() {
            override fun areItemsTheSame(
                oldItem: IngredientType,
                newItem: IngredientType
            ): Boolean {
                return oldItem.id == 0 && newItem.id == 0
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: IngredientType,
                newItem: IngredientType
            ): Boolean {
                return oldItem === newItem
            }
        }
    }
}