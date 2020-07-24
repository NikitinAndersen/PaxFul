package com.example.test1.domain.interactors

import com.example.test1.data.converter.JokeConverter
import com.example.test1.data.db.SharedPreferenceManager
import com.example.test1.data.db.joke.JokesDao
import com.example.test1.data.repository.AppRepository
import com.example.test1.domain.JokeInteractor
import com.example.test1.extensions.getNullIfBlank
import com.example.test1.data.model.BaseJokesResponse
import com.example.test1.data.model.Joke
import com.example.test1.ui.base.StateAwareResponse
import com.example.test1.ui.base.loadedState

class JokeInteractorImpl(
    private val dao: JokesDao,
    private val repository: AppRepository,
    private val sharedPrefs: SharedPreferenceManager,
    private val converter: JokeConverter
) : JokeInteractor(sharedPrefs) {

    override suspend fun getAllJokes(): StateAwareResponse<List<Joke>> {
        if (sharedPrefs.getIsOfflineMode()) {
            return StateAwareResponse.Loaded(emptyList())
        }
        val response: BaseJokesResponse
        response = if (isNeedToChangeName()) {
            val firstName = sharedPrefs.getFirstName()
            val lastName = sharedPrefs.getLastName()
            repository.getJokes(firstName.getNullIfBlank(), lastName.getNullIfBlank())
        } else {
            repository.getJokes()
        }
        return response.value.loadedState
    }

    override suspend fun getRandomJoke(): StateAwareResponse<Joke?> {
        val daoResponse = dao.getRandomJoke() ?: return  StateAwareResponse.Loaded(null)

        val convertedJoke = converter.convertJokeEntityToJoke(daoResponse)
        if (isNeedToChangeName()) {
            return converter.changeNameToCustom(convertedJoke).loadedState
        }
        return convertedJoke.loadedState
    }

    override suspend fun likeJoke(joke: Joke) {
        dao.insertJoke(converter.convertJokeToJokeEntity(joke, true))
    }

    override suspend fun isEnabledShake(): Boolean {
        return sharedPrefs.getIsOfflineMode()
    }
}