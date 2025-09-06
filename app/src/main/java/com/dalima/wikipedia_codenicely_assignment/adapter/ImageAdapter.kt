package com.dalima.wikipedia_codenicely_assignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dalima.wikipedia_codenicely_assignment.databinding.ItemImageBinding
import com.dalima.wikipedia_codenicely_assignment.db.ImageEntity

class ImageAdapter : ListAdapter<ImageEntity, ImageAdapter.VH>(Diff) {
    object Diff : DiffUtil.ItemCallback<ImageEntity>() {
        override fun areItemsTheSame(o: ImageEntity, n: ImageEntity) = o.url == n.url
        override fun areContentsTheSame(o: ImageEntity, n: ImageEntity) = o == n
    }

    inner class VH(private val b: ItemImageBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(item: ImageEntity) {
            Glide.with(b.imgPreview.context).load(item.url).into(b.imgPreview)
            b.tvInfo.text = item.title ?: "Image"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position))
}