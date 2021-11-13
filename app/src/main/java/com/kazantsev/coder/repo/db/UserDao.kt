package com.kazantsev.coder.repo.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kazantsev.coder.repo.db.model.UsersDb

@Dao
interface UserDao {
    @Query("DELETE  FROM users")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(posts: List<UsersDb>)

    @Query("SELECT * FROM users ORDER BY name ASC")
     suspend fun getAllUsers(): List<UsersDb>

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUser(id: String): UsersDb

}