package com.dalima.wikipedia_codenicely_assignment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dalima.wikipedia_codenicely_assignment.databinding.ItemArticleBinding

class ArticleAdapter(private val items: MutableList<ArticleEntity> = mutableListOf()) :
    RecyclerView.Adapter<ArticleAdapter.VH>() {

    fun submitList(newItems: List<ArticleEntity>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun append(newItems: List<ArticleEntity>) {
        val start = items.size
        items.addAll(newItems)
        notifyItemRangeInserted(start, newItems.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun getItemCount() = items.size
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    inner class VH(private val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ArticleEntity) {
            binding.tvTitle.text = item.title
            binding.tvSnippet.text = item.snippet ?: ""
            // click to open web page can be added
        }
    }
}