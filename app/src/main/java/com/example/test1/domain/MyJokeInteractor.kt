package com.example.test1.domain

import com.example.test1.data.db.SharedPreferenceManager
import com.example.test1.data.model.Joke
import com.example.test1.ui.base.StateAwareResponse

abstract class MyJokeInteractor(
    sharedPrefs: SharedPreferenceManager
) : BaseInteractor(sharedPrefs) {

    abstract suspend fun saveNewJoke(jokeText: String)

    abstract suspend fun getSavedJokes(): StateAwareResponse<List<Joke>>

    abstract suspend fun deleteJoke(joke: Joke)
}