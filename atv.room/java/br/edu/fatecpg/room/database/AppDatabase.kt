package br.edu.fatecpg.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.edu.fatecpg.room.dao.UserDao
import br.edu.fatecpg.room.model.User

@Database(entities = [User::class], version = 2)
abstract class AppDatabase:RoomDatabase() {
    abstract fun userDao():UserDao
}
