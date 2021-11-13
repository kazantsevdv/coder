package com.kazantsev.coder.view.adapter.vhitem

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import com.kazantsev.coder.R
import com.kazantsev.coder.databinding.ItemBirthdayBinding
import com.kazantsev.coder.repo.image.ImageLoader
import com.kazantsev.coder.view.adapter.BaseViewHolder
import com.kazantsev.coder.view.adapter.OnListItemClickListener
import com.kazantsev.coder.view.mainfragment.DataItem

class RecordVHListDate : ItemVHList<ItemBirthdayBinding, DataItem.ItemBirthday> {

    override fun isRelativeItem(item: DataItem) = item is DataItem.ItemBirthday

    override fun getLayoutId() = R.layout.item_birthday

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<ItemBirthdayBinding, DataItem.ItemBirthday> {
        val binding = ItemBirthdayBinding.inflate(layoutInflater, parent, false)
        return RecordViewHolderBirthday(binding)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<DataItem.ItemBirthday>() {
        override fun areItemsTheSame(
            oldItem: DataItem.ItemBirthday,
            newItem: DataItem.ItemBirthday
        ) =
            oldItem.id == newItem.id


        override fun areContentsTheSame(
            oldItem: DataItem.ItemBirthday,
            newItem: DataItem.ItemBirthday
        ) =
            oldItem == newItem
    }

}

class RecordViewHolderBirthday(
    binding: ItemBirthdayBinding
) : BaseViewHolder<ItemBirthdayBinding, DataItem.ItemBirthday>(binding) {

    override fun onBind(
        item: DataItem.ItemBirthday,
        imageLoader: ImageLoader<ImageView>,
        onListItemClickListener: OnListItemClickListener
    ) {
        binding.tvName.text = item.name
        binding.tvNik.text = item.userTag
        binding.tvPosition.text = item.position
        binding.tvBirthday.text = item.birthday
        imageLoader.loadInto(item.avatarUrl, binding.ivAvatar)
        binding.root.setOnClickListener { onListItemClickListener.onItemClick(item.id) }
    }


}
