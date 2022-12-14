package com.angdroid.refrigerator_manament.presentation.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.angdroid.refrigerator_manament.BR
import com.angdroid.refrigerator_manament.databinding.ItemSearchListBinding

//TODO 현재 사용처 없음
class SearchAdapter(
    context: Context, private val itemClickListener: (String) -> Unit,
) : ListAdapter<String, RecyclerView.ViewHolder>(SearchListDiffCallBack) {

    private val inflater by lazy { LayoutInflater.from(context) }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SearchItemViewHolder(
            ItemSearchListBinding.inflate(inflater, parent, false)
        )
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = getItem(position)
        with(holder as SearchItemViewHolder) {
            binding.setVariable(BR.result, currentItem)
            binding.root.setOnClickListener { itemClickListener(currentItem) }
        }
    }


    class SearchItemViewHolder(val binding: ItemSearchListBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {

        private object SearchListDiffCallBack : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(
                oldItem: String,
                newItem: String
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: String,
                newItem: String
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}