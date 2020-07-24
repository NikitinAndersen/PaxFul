package com.example.test1.data.db.joke

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_jokes")
data class JokesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val joke: String,
    val isLiked: Boolean
)