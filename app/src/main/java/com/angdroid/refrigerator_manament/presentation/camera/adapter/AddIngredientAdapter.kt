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
import timber.log.Timber

class AddIngredientAdapter(
    context: Context,
    private val itemClickListener: (IngredientType.Food) -> Unit,
    private val itemAddListener: (IngredientType.Food) -> Unit
) : ListAdapter<IngredientType.Food, RecyclerView.ViewHolder>(IngredientDiffCallBack) {
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
                    binding.root.setOnClickListener {
                        itemClickListener(currentItem) //TODO 아이템 개수 조정 View 분리
                        minusItemCount(position, currentItem)
                    }
                    binding.ivDelete.setOnClickListener {
                        removeItem(position)
                    }
                }
            }
            SELF -> {
                with(holder as SelfAddViewHolder) {
                    binding.root.setOnClickListener {
                        Timber.e("customDiaglog")
                        itemAddListener
                        //TODO CustomDialog show
                    }
                }
            }

        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).fid.toInt() == 0) SELF else INGREDIENTS
    //fid가 0인 더미데이터를 넣어줘서 [직접추가]아이템이 나오도록

    // return if (position == currentList.size - 1) SELF else INGREDIENTS
        //마지막 아이템을 무조건 [직접 추가]아이템이 나오도록
    //하지만 둘다 경우 마지막에 프론트단에서 무조건 더미데이터 하나를 넣어주어야 함..
    }

    private fun removeItem(position: Int) {
        val currentList = currentList.toMutableList()
        currentList.removeAt(position)
        submitList(currentList)
    }

    private fun minusItemCount(position: Int, currentItem: IngredientType.Food) {
        val currentList = currentList.toMutableList()
        if (currentItem.foodCount == 1)
            removeItem(position)
        else {
            currentList[position] =
                IngredientType.Food(
                    currentItem.fid,
                    currentItem.foodId,
                    currentItem.expirationDate,
                    currentItem.name,
                    currentItem.image,
                    currentItem.categoryId,
                    currentItem.foodCount - 1
                )
            submitList(currentList)
        }
    }

    private fun plusItemCount(position: Int, currentItem: IngredientType.Food) {
        val currentList = currentList.toMutableList()
        if (currentItem.foodCount == 1)
            removeItem(position)
        else {
            currentList[position] =
                IngredientType.Food(
                    currentItem.fid,
                    currentItem.foodId,
                    currentItem.expirationDate,
                    currentItem.name,
                    currentItem.image,
                    currentItem.categoryId,
                    currentItem.foodCount + 1
                )
            submitList(currentList)
        }
    }

    companion object {
        const val INGREDIENTS = 1
        const val SELF = 2

        private object IngredientDiffCallBack : DiffUtil.ItemCallback<IngredientType.Food>() {
            override fun areItemsTheSame(
                oldItem: IngredientType.Food,
                newItem: IngredientType.Food
            ): Boolean {
                return oldItem.foodId == newItem.foodId
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: IngredientType.Food,
                newItem: IngredientType.Food
            ): Boolean {
                return oldItem === newItem
            }
        }
    }
}