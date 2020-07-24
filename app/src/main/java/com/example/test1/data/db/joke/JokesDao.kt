package com.example.test1.data.db.joke

import androidx.room.*

@Dao
interface JokesDao {
    @Transaction
    @Query("SELECT * FROM favorite_jokes")
    suspend fun getAllJokes(): List<JokesEntity>?

    @Transaction
    @Query("SELECT * FROM favorite_jokes ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomJoke(): JokesEntity?

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJoke(jokesEntity: JokesEntity)

    @Transaction
    @Query("DELETE FROM favorite_jokes WHERE id IS :id")
    suspend fun deleteJokes(id:Int)

    @Transaction
    @Query("DELETE FROM favorite_jokes")
    suspend fun deleteAllJokes()
}
