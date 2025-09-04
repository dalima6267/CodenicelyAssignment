package com.dalima.wikipedia_codenicely_assignment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dalima.wikipedia_codenicely_assignment.databinding.ItemImageBinding

class ImageAdapter(private val items: MutableList<ImageEntity> = mutableListOf()) :
    RecyclerView.Adapter<ImageAdapter.VH>() {

    fun submitList(newItems: List<ImageEntity>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun getItemCount() = items.size
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    inner class VH(private val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ImageEntity) {
            Glide.with(binding.imgPreview.context)
                .load(item.url)
                .centerCrop()
                .into(binding.imgPreview)
            binding.tvInfo.text = "By ${item.user ?: "unknown"} • ${item.timestamp ?: ""}"
        }
    }
}