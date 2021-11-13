package com.kazantsev.coder.view.adapter.vhitem

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import com.kazantsev.coder.R
import com.kazantsev.coder.databinding.ItemNameBinding
import com.kazantsev.coder.repo.image.ImageLoader
import com.kazantsev.coder.view.adapter.BaseViewHolder
import com.kazantsev.coder.view.adapter.OnListItemClickListener
import com.kazantsev.coder.view.mainfragment.DataItem

class RecordVHListName : ItemVHList<ItemNameBinding, DataItem.ItemName> {

    override fun isRelativeItem(item: DataItem) = item is DataItem.ItemName

    override fun getLayoutId() = R.layout.item_delimiter

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<ItemNameBinding, DataItem.ItemName> {
        val binding = ItemNameBinding.inflate(layoutInflater, parent, false)
        return RecordViewHolderName(binding)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<DataItem.ItemName>() {
        override fun areItemsTheSame(oldItem: DataItem.ItemName, newItem: DataItem.ItemName) =
            oldItem.id == newItem.id


        override fun areContentsTheSame(oldItem: DataItem.ItemName, newItem: DataItem.ItemName) =
            oldItem == newItem
    }

}

class RecordViewHolderName(
    binding: ItemNameBinding
) : BaseViewHolder<ItemNameBinding, DataItem.ItemName>(binding) {

    override fun onBind(
        item: DataItem.ItemName,
        imageLoader: ImageLoader<ImageView>,
        onListItemClickListener: OnListItemClickListener
    ) {
        binding.tvName.text = item.name
        binding.tvNik.text = item.userTag
        binding.tvPosition.text = item.position
        imageLoader.loadInto(item.avatarUrl, binding.ivAvatar)
        binding.root.setOnClickListener { onListItemClickListener.onItemClick(item) }
    }


}
