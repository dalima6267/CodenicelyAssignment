package com.dalima.wikipedia_codenicely_assignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dalima.wikipedia_codenicely_assignment.databinding.ItemCategoryBinding
import com.dalima.wikipedia_codenicely_assignment.db.CategoryEntity

class CategoryAdapter :
    ListAdapter<CategoryEntity, CategoryAdapter.CategoryViewHolder>(DiffCallback) {

    object DiffCallback : DiffUtil.ItemCallback<CategoryEntity>() {
        override fun areItemsTheSame(oldItem: CategoryEntity, newItem: CategoryEntity) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: CategoryEntity, newItem: CategoryEntity) =
            oldItem == newItem
    }

    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CategoryEntity) {
            binding.categoryName.text = item.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}