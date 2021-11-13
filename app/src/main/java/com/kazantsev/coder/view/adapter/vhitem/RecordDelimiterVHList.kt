package com.kazantsev.coder.view.adapter.vhitem

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import com.kazantsev.coder.R
import com.kazantsev.coder.databinding.ItemDelimiterBinding
import com.kazantsev.coder.repo.image.ImageLoader
import com.kazantsev.coder.view.adapter.BaseViewHolder
import com.kazantsev.coder.view.adapter.OnListItemClickListener
import com.kazantsev.coder.view.mainfragment.DataItem

class RecordDelimiterVHList : ItemVHList<ItemDelimiterBinding, DataItem.Delimiter> {

    override fun isRelativeItem(item: DataItem) = item is DataItem.Delimiter

    override fun getLayoutId() = R.layout.item_delimiter

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<ItemDelimiterBinding, DataItem.Delimiter> {
        val binding = ItemDelimiterBinding.inflate(layoutInflater, parent, false)
        return DelimiterViewHolder(binding)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<DataItem.Delimiter>() {
        override fun areItemsTheSame(oldItem: DataItem.Delimiter, newItem: DataItem.Delimiter) =
            oldItem.date == newItem.date

        override fun areContentsTheSame(oldItem: DataItem.Delimiter, newItem: DataItem.Delimiter) =
            oldItem == newItem
    }
}

class DelimiterViewHolder(
    binding: ItemDelimiterBinding
) : BaseViewHolder<ItemDelimiterBinding, DataItem.Delimiter>(binding) {

    override fun onBind(
        item: DataItem.Delimiter,
        imageLoader: ImageLoader<ImageView>,
        onListItemClickListener: OnListItemClickListener
    ) {
        binding.tvYear.text = item.date
    }
}
