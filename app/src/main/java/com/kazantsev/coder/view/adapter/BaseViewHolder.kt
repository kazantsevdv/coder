package com.kazantsev.coder.view.adapter

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.kazantsev.coder.repo.image.ImageLoader
import com.kazantsev.coder.view.mainfragment.DataItem

abstract class BaseViewHolder<out V : ViewBinding, in I : DataItem>(
    val binding: V
) : RecyclerView.ViewHolder(binding.root) {

    abstract fun onBind(
        item: I,
        imageLoader: ImageLoader<ImageView>,
        onListItemClickListener: OnListItemClickListener
    )
}