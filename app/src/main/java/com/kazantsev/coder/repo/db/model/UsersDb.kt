package com.kazantsev.coder.repo.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "users")
data class UsersDb(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "avatarUrl") val avatarUrl: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "userTag") val userTag: String,
    @ColumnInfo(name = "department") val department: String,
    @ColumnInfo(name = "position") val position: String,
    @ColumnInfo(name = "birthday") val birthday: Long,
    @ColumnInfo(name = "phone") val phone: String
)