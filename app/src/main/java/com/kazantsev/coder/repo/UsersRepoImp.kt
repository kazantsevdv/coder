package com.kazantsev.coder.repo

import com.kazantsev.coder.repo.api.DataSource
import com.kazantsev.coder.repo.api.model.UsersApi
import com.kazantsev.coder.repo.db.UsersDatabase

class UsersRepoImp(private val api: DataSource, private val db: UsersDatabase) : UsersRepo {
    override suspend fun getUsers(): List<UsersApi> {

        return api.getUsers().body()!!.items

    }
}