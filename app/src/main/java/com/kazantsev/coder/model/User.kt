package com.kazantsev.coder.model

import com.kazantsev.coder.repo.db.model.UsersDb

data class User(
    val id: String,
    val avatarUrl: String,
    val name: String,
    val userTag: String,
    val department: String,
    val position: String,
    val birthday: Long,
    val phone: String
)

fun UsersDb.toUser(): User {
    return User(
        id = id,
        avatarUrl = avatarUrl,
        name = name,
        userTag = userTag,
        department = department,
        position = position,
        birthday = birthday,
        phone = phone
    )
}