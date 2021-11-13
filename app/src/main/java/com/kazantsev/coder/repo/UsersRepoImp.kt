package com.kazantsev.coder.repo

import com.kazantsev.coder.model.User
import com.kazantsev.coder.model.toUser
import com.kazantsev.coder.repo.api.DataSource
import com.kazantsev.coder.repo.api.model.UsersApi
import com.kazantsev.coder.repo.api.model.toUsersDb
import com.kazantsev.coder.repo.db.UsersDatabase

class UsersRepoImp(private val api: DataSource, private val db: UsersDatabase) : UsersRepo {

    override suspend fun getUsersFromApi(): List<UsersApi> {
//        try {
            val response = api.getUsers()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {resp->
                    db.dao.deleteAll()
                    db.dao.insertUsers(resp.items.map { it.toUsersDb() })
                    return body.items
                }
            }

        return listOf()
//           return error("${response.code()} ${response.message()}")
//        } catch (e: Exception) {
//                    return error(e.message ?: e.toString())
//        }

    }

    override suspend fun getUser(id: String): User {
        return db.dao.getUser(id).toUser()
    }

    override suspend fun getUsers(): List<User> {
        return db.dao.getAllUsers().map { it.toUser() }
    }
}