package com.example.test1.domain.interactors

import com.example.test1.data.converter.JokeConverter
import com.example.test1.data.db.SharedPreferenceManager
import com.example.test1.data.db.joke.JokesDao
import com.example.test1.data.db.joke.JokesEntity
import com.example.test1.domain.MyJokeInteractor
import com.example.test1.data.model.Joke
import com.example.test1.ui.base.StateAwareResponse
import com.example.test1.ui.base.loadedState

class MyJokeInteractorImpl(
    private val dao: JokesDao,
    sharedPrefs: SharedPreferenceManager,
    private val converter: JokeConverter
) : MyJokeInteractor(sharedPrefs) {

    override suspend fun saveNewJoke(jokeText: String) {
        dao.insertJoke(JokesEntity(joke = jokeText, isLiked = false))
    }

    override suspend fun getSavedJokes(): StateAwareResponse<List<Joke>> {
        val result = dao.getAllJokes() ?: return StateAwareResponse.Loaded(listOf())

        val originalJokes = result.map(converter::convertJokeEntityToJoke)
        if (isNeedToChangeName()) {
            return originalJokes.map(converter::changeNameToCustom).loadedState
        }
        return originalJokes.loadedState
    }

    override suspend fun deleteJoke(joke: Joke) {
        dao.deleteJokes(joke.id)
    }
}