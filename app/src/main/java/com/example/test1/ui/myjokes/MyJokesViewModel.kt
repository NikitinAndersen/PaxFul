package com.example.test1.ui.myjokes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.test1.data.model.Joke
import com.example.test1.domain.MyJokeInteractor
import com.example.test1.extensions.asImmutable
import com.example.test1.extensions.isError
import com.example.test1.extensions.isSuccess
import com.example.test1.ui.base.BaseViewModel

class MyJokesViewModel(
    savedStateHandle: SavedStateHandle,
    private val interactor: MyJokeInteractor
) : BaseViewModel() {

    private val jokes: MutableLiveData<List<Joke>> = savedStateHandle.getLiveData(JOKES_KEY)
    fun jokes() = jokes.asImmutable()

    init {
        loadData()
    }

    private fun loadData() = runCoroutine {
        withProgress { interactor.getSavedJokes() }
            .isError(::showError)
            .isSuccess {
                jokes.value = it
            }
    }

    fun updateData() {
        loadData()
    }

    fun deleteJoke(joke: Joke) = runCoroutine {
        withProgress {
            interactor.deleteJoke(joke)
            interactor.getSavedJokes()
        }
            .isError(::showError)
            .isSuccess {
                jokes.value = it
            }
    }

    companion object {
        private const val JOKES_KEY = "JOKES_KEY"
    }
}