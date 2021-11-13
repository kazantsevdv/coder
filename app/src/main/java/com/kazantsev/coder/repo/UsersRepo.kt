package com.kazantsev.coder.repo

import com.kazantsev.coder.model.User
import com.kazantsev.coder.repo.api.model.UsersApi

interface UsersRepo {
   suspend fun getUsers(): List<UsersApi>
   suspend fun getUser(id: String): User
}