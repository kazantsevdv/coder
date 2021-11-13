package com.kazantsev.coder.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.kazantsev.coder.repo.image.ImageLoader
import com.kazantsev.coder.view.adapter.vhitem.ItemVHList
import com.kazantsev.coder.view.mainfragment.DataItem

class RvAdapter(
    private val VHLists: List<ItemVHList<*, *>>,
    private val imageLoader: ImageLoader<ImageView>,
    private var onListItemClickListener: OnListItemClickListener
) : ListAdapter<DataItem, BaseViewHolder<ViewBinding, DataItem>>(
    ItemDiffUtil(VHLists)
) {
    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ViewBinding, DataItem> {
        val inflater = LayoutInflater.from(parent.context)
        return VHLists.find { it.getLayoutId() == viewType }
            ?.getViewHolder(inflater, parent)
            ?.let { it as BaseViewHolder<ViewBinding, DataItem> }
            ?: throw IllegalArgumentException("View type not found: $viewType")
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ViewBinding, DataItem>, position: Int) {
        holder.onBind(currentList[position], imageLoader, onListItemClickListener)
    }

    override fun getItemViewType(position: Int): Int {
        val item = currentList[position]
        return VHLists.find { it.isRelativeItem(item) }
            ?.getLayoutId()
            ?: throw IllegalArgumentException("View type not found: $item")
    }


    class ItemDiffUtil(
        private val fingerprints: List<ItemVHList<*, *>>,
    ) : DiffUtil.ItemCallback<DataItem>() {

        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            if (oldItem::class != newItem::class) return false

            return getItemCallback(oldItem).areItemsTheSame(oldItem, newItem)
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            if (oldItem::class != newItem::class) return false

            return getItemCallback(oldItem).areContentsTheSame(oldItem, newItem)
        }

        @Suppress("UNCHECKED_CAST")
        private fun getItemCallback(
            item: DataItem
        ): DiffUtil.ItemCallback<DataItem> = fingerprints.find { it.isRelativeItem(item) }
            ?.getDiffUtil()
            ?.let { it as DiffUtil.ItemCallback<DataItem> }
            ?: throw IllegalStateException("DiffUtil not found for $item")

    }
}

interface OnListItemClickListener {
    fun onItemClick(data: DataItem)
}