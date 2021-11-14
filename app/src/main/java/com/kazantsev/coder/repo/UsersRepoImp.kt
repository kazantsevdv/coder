package com.kazantsev.coder.repo

import android.accounts.NetworkErrorException
import com.kazantsev.coder.model.User
import com.kazantsev.coder.model.toUser
import com.kazantsev.coder.repo.api.DataSource
import com.kazantsev.coder.repo.api.model.toUsersDb
import com.kazantsev.coder.repo.db.UsersDatabase
import com.kazantsev.coder.view.mainfragment.Department

class UsersRepoImp(private val api: DataSource, private val db: UsersDatabase) : UsersRepo {

    override suspend fun getUsersFromApi() {
        val response = api.getUsers()
        if (response.isSuccessful) {
            val body = response.body()
            body?.let { resp ->
                db.dao.deleteAll()
                db.dao.insertUsers(resp.items.map { it.toUsersDb() })
            }

        } else {
            throw NetworkErrorException("Server error")
        }
    }

    override suspend fun getUser(id: String): User {
        return db.dao.getUser(id).toUser()
    }

    override suspend fun getUsers(): List<User> {
        return db.dao.getAllUsers().map { it.toUser() }
    }

    override fun getDepartment() = listOf(
        Department("Все", ""),
        Department("Android", "android"),
        Department("iOS", "ios"),
        Department("Дизайн", "design"),
        Department("Менеджмент", "management"),
        Department("QA", "qa"),
        Department("HR", "hr"),
        Department("PR", "pr"),
        Department("Backend", "backend"),
        Department("Техподдержка", "support"),
        Department("Аналитика", "analytics")
    )
}