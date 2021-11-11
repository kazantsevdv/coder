package com.kazantsev.coder.di


import androidx.room.Room
import com.kazantsev.coder.App
import com.kazantsev.coder.repo.db.UsersDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {
    @Singleton
    @Provides
    fun provideDatabase(app: App): UsersDatabase =
        Room.databaseBuilder(app, UsersDatabase::class.java, "db.db")
            .fallbackToDestructiveMigration()
            .build()
}