package com.dalima.wikipedia_codenicely_assignment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dalima.wikipedia_codenicely_assignment.databinding.ItemArticleBinding

class ArticleAdapter :
    ListAdapter<ArticleEntity, ArticleAdapter.VH>(Diff) {

    object Diff : DiffUtil.ItemCallback<ArticleEntity>() {
        override fun areItemsTheSame(old: ArticleEntity, new: ArticleEntity) = old.pageId == new.pageId
        override fun areContentsTheSame(old: ArticleEntity, new: ArticleEntity) = old == new
    }

    inner class VH(private val b: ItemArticleBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(item: ArticleEntity) {
            b.tvTitle.text = item.title
            b.tvSnippet.text = item.snippet ?: ""
            if (!item.imageUrl.isNullOrBlank()) {
                Glide.with(b.articleImage.context).load(item.imageUrl).into(b.articleImage)
                b.articleImage.visibility = android.view.View.VISIBLE
            } else b.articleImage.visibility = android.view.View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position))
}
