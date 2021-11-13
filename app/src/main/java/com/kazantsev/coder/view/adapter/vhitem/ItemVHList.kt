package com.kazantsev.coder.view.adapter.vhitem

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.kazantsev.coder.view.adapter.BaseViewHolder
import com.kazantsev.coder.view.mainfragment.DataItem

interface ItemVHList<V : ViewBinding, I : DataItem> {

    fun isRelativeItem(item: DataItem): Boolean

    @LayoutRes
    fun getLayoutId(): Int

    fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<V, I>

    fun getDiffUtil(): DiffUtil.ItemCallback<I>
}