package com.kazantsev.coder.di

import com.kazantsev.coder.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {


    @Singleton
    @Provides
    fun app(): App {
        return app
    }

}