package com.example.test1.data.converter

import com.example.test1.data.db.joke.JokesEntity
import com.example.test1.data.model.Joke

interface JokeConverter {

    fun convertJokeEntityToJoke(jokesEntity: JokesEntity): Joke

    fun convertJokeToJokeEntity(joke: Joke, isLiked: Boolean): JokesEntity

    fun changeNameToCustom(item: Joke): Joke
}