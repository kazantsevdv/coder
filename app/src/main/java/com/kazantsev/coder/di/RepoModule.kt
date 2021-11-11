package com.kazantsev.coder.di


import com.kazantsev.coder.repo.UsersRepo
import com.kazantsev.coder.repo.UsersRepoImp
import com.kazantsev.coder.repo.api.DataSource
import com.kazantsev.coder.repo.db.UsersDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule {
    @Singleton
    @Provides
    fun provideRepo(
        api: DataSource,
        db: UsersDatabase
    ): UsersRepo = UsersRepoImp(api, db)

}