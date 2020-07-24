package com.example.test1.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.test1.core.SingleLiveEvent
import com.example.test1.data.model.Joke
import com.example.test1.domain.JokeInteractor
import com.example.test1.extensions.asImmutable
import com.example.test1.extensions.isError
import com.example.test1.extensions.isSuccess
import com.example.test1.ui.base.BaseViewModel

class JokesViewModel(
    savedStateHandle: SavedStateHandle,
    private val interactor: JokeInteractor
) : BaseViewModel() {

    private val jokes = MutableLiveData<List<Joke>>()
    private val randomJoke: MutableLiveData<Joke> = savedStateHandle.getLiveData(RANDOM_JOKES_KEY)
    private val jokeToShare = SingleLiveEvent<String>()
    private val jokeLiked = SingleLiveEvent<Unit>()
    private val isEnableShake = MutableLiveData<Boolean>()

    fun jokes() = jokes.asImmutable()
    fun getJokeToShare() = jokeToShare
    fun getJokeLiked() = jokeLiked
    fun getRandomJoke() = randomJoke.asImmutable()
    fun getIsEnableShake() = isEnableShake.asImmutable()

    init {
        loadData()
    }

    private fun loadData() = runCoroutine {
        isEnableShake.value = interactor.isEnabledShake()
        withProgress {
            interactor.getAllJokes()
        }
            .isError(::showError)
            .isSuccess {
                isEmptyView.value = it.isEmpty() && randomJoke.value == null
                jokes.value = it
            }
    }

    fun pullToRefresh() = runCoroutine {
        interactor.getAllJokes()
            .isError(::showError)
            .isSuccess {
                isLoading.value = false
                jokes.value = it
            }
    }

    fun onLikeClicked(joke: Joke) {
        runCoroutine {
            interactor.likeJoke(joke)
            jokeLiked.call()
        }
    }

    fun onShareClicked(joke: Joke) {
        jokeToShare.value = joke.joke
    }

    fun onDeviceShacked() {
        runCoroutine {
            isEnableShake.value = false
            withProgress { interactor.getRandomJoke() }
                .isError(::showError)
                .isSuccess {
                    if (it == null) {
                        isEmptyView.value = true
                    } else {
                        isEmptyView.value = false
                        randomJoke.value = it
                    }
                }
            isEnableShake.value = interactor.isEnabledShake()
        }
    }

    companion object {
        private const val RANDOM_JOKES_KEY = "RANDOM_JOKES_KEY"
    }

}