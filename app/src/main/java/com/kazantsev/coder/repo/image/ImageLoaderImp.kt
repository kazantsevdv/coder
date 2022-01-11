package com.kazantsev.coder.repo.image

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.kazantsev.coder.R

class ImageLoaderImp : ImageLoader<ImageView> {

    override fun loadInto(url: String, container: ImageView) {

        Glide.with(container.context)
            .asBitmap()
            .placeholder(R.drawable.stub)
            .circleCrop()
            .load(url)
            .into(container)
    }

}