package com.example.test1.domain

import com.example.test1.data.db.SharedPreferenceManager
import com.example.test1.data.model.Joke
import com.example.test1.ui.base.StateAwareResponse

abstract class JokeInteractor(
    sharedPrefs: SharedPreferenceManager
) : BaseInteractor(sharedPrefs) {

    abstract suspend fun getAllJokes(): StateAwareResponse<List<Joke>>

    abstract suspend fun getRandomJoke(): StateAwareResponse<Joke?>

    abstract suspend fun likeJoke(joke: Joke)

    abstract suspend fun isEnabledShake(): Boolean

}