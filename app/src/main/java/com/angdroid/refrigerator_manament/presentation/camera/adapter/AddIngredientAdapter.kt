package com.angdroid.refrigerator_manament.presentation.camera.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.angdroid.refrigerator_manament.BR
import com.angdroid.refrigerator_manament.databinding.ItemIngredientsBinding
import com.angdroid.refrigerator_manament.databinding.ItemSelfIngredientsBinding
import com.angdroid.refrigerator_manament.domain.entity.model.IngredientType
import com.angdroid.refrigerator_manament.presentation.util.setOnSingleClickListener

class AddIngredientAdapter(
    val context: Context,
    private val itemDeleteListener: (IngredientType.Food) -> Unit,
    private val itemMinusListener: (IngredientType.Food) -> Unit,
    private val itemPlusListener: (IngredientType.Food) -> Unit,
    private val itemDialogListener: (IngredientType.Food) -> Unit

) : ListAdapter<IngredientType.Food, RecyclerView.ViewHolder>(IngredientDiffCallBack) {
    private val inflater by lazy { LayoutInflater.from(context) }

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

    class IngredientViewHolder(val binding: ItemIngredientsBinding) :
        RecyclerView.ViewHolder(binding.root)

    class SelfAddViewHolder(val binding: ItemSelfIngredientsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = getItem(position)
        when (holder.itemViewType) {
            INGREDIENTS -> {
                with(holder as IngredientViewHolder) {
                    binding.apply {
                        setVariable(BR.food, (currentItem as IngredientType.Food))
                        ivMinus.setOnSingleClickListener { itemMinusListener(currentItem) }
                        ivPlus.setOnSingleClickListener { itemPlusListener(currentItem) }
                        ivDelete.setOnSingleClickListener { itemDeleteListener(currentItem) }
                    }
                }
            }
            SELF -> {
                with(holder as SelfAddViewHolder) {
                    binding.root.setOnClickListener {
                        itemDialogListener(currentItem)
                    }
                }
            }

        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).fid == "0") SELF else INGREDIENTS
        //fid??? 0??? ?????????????????? ???????????? [????????????]???????????? ????????????

        // return if (position == currentList.size - 1) SELF else INGREDIENTS
        //????????? ???????????? ????????? [?????? ??????]???????????? ????????????
        //????????? ?????? ?????? ???????????? ?????????????????? ????????? ??????????????? ????????? ??????????????? ???..
    }

    companion object {
        const val INGREDIENTS = 1
        const val SELF = 2

        private object IngredientDiffCallBack : DiffUtil.ItemCallback<IngredientType.Food>() {
            override fun areItemsTheSame(
                oldItem: IngredientType.Food,
                newItem: IngredientType.Food
            ): Boolean {
                return oldItem.fid === newItem.fid
            }

            override fun areContentsTheSame(
                oldItem: IngredientType.Food,
                newItem: IngredientType.Food
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}