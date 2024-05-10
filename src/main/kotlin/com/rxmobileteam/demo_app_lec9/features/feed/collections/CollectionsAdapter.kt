package com.demo.chungha.demo.android_005.demo_app_lec9.features.feed.collections

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.demo.chungha.demo.android_005.databinding.ItemCollectionLayoutBinding
import com.demo.chungha.demo.android_005.demo_app_lec9.features.feed.collections.CollectionsUiState.Item

object CollectionsUiStateItemCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Item, newItem: Item) = oldItem == newItem
}

class CollectionsAdapter(
    private val requestManager: RequestManager,
) : ListAdapter<Item, CollectionsAdapter.VH>(CollectionsUiStateItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
        ItemCollectionLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position))

    inner class VH(
        private val binding: ItemCollectionLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.run {
                textTitle.text = item.title
                textDesc.text = item.description

                requestManager
                    .load(item.coverPhotoUrl)
                    .fitCenter()
                    .centerCrop()
                    .transition(withCrossFade())
                    .into(imageView)
            }
        }
    }
}