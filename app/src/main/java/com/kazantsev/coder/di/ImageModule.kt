package com.kazantsev.coder.di

import android.widget.ImageView
import com.kazantsev.coder.repo.image.ImageLoader
import com.kazantsev.coder.repo.image.ImageLoaderImp
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class ImageModule {
    @Singleton
    @Provides
    fun provideImageLoader(): ImageLoader<ImageView> =
        ImageLoaderImp()

}