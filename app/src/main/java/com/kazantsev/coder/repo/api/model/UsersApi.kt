package com.kazantsev.coder.repo.api.model

import com.google.gson.annotations.SerializedName
import com.kazantsev.coder.repo.db.model.UsersDb
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

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
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return UsersDb(
        id = id,
        avatarUrl = avatarUrl,
        name = "$firstName $lastName",
        userTag = userTag,
        department = department,
        position = position,
        birthday = try{format.parse(birthday)?.time?:0}catch (e:Exception){0},
        phone = phone.filter { it!='-' }
    )
}