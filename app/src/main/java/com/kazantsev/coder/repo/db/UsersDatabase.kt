package com.kazantsev.coder.repo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kazantsev.coder.repo.db.model.UsersDb

@Database(entities = [UsersDb::class], version = 1, exportSchema = false)
abstract class UsersDatabase: RoomDatabase() {
    abstract val dao: UserDao
}