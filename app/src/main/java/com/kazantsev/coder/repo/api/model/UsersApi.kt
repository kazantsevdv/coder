package com.kazantsev.coder.repo.api.model

import com.google.gson.annotations.SerializedName
import com.kazantsev.coder.repo.db.model.UsersDb

data class UsersApi(
    @SerializedName("id") val id: String,
    @SerializedName("avatarUrl") val avatarUrl: String,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("userTag") val userTag: String,
    @SerializedName("department") val department: String,
    @SerializedName("position") val position: String,
    @SerializedName("birthday") val birthday: String,
    @SerializedName("phone") val phone: String
)

fun UsersApi.toUsersDb(): UsersDb {
    return UsersDb(
        id = id,
        avatarUrl = avatarUrl,
        firstName = firstName,
        lastName = lastName,
        userTag = userTag,
        department = department,
        position = position,
        birthday = birthday,
        phone = phone
    )
}