package com.kazantsev.coder.repo.image

import android.widget.ImageView
import com.bumptech.glide.Glide

class ImageLoaderImp : ImageLoader<ImageView> {

    override fun loadInto(url: String, container: ImageView) {

        Glide.with(container.context)
            .asBitmap()
            .circleCrop()
            .load(url)
            .into(container)
    }

}