package com.example.test1.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.test1.data.db.joke.JokesDao
import com.example.test1.data.db.joke.JokesEntity

private const val VERSION = 2

@Database(
    entities = [JokesEntity::class],
    version = VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getDao(): JokesDao

}
