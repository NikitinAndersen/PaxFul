package com.example.test1.ui.myjokes.addjokedialog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.test1.core.SingleLiveEvent
import com.example.test1.domain.MyJokeInteractor
import com.example.test1.extensions.asImmutable
import com.example.test1.ui.base.BaseViewModel

class AddJokeViewModel(
    savedStateHandle: SavedStateHandle,
    private val interactor: MyJokeInteractor
) : BaseViewModel() {

    private val isEnableSaveButton = SingleLiveEvent<Boolean>()
    private val isDismiss = SingleLiveEvent<Unit>()
    private val jokeText: MutableLiveData<String> = savedStateHandle.getLiveData(JOKE_TEXT_KEY)
    private var joke: String = ""

    fun getJokeText() = jokeText.asImmutable()
    fun getIsDismiss() = isDismiss
    fun getIsEnabledSaveButton() = isEnableSaveButton


    fun onSaveJokeClicked(jokeText: String) {
        runCoroutine {
            isEnableSaveButton.value = false
            interactor.saveNewJoke(jokeText)
            isDismiss.postCall()
        }
    }

    fun textTextChanged(text: String) {
        isEnableSaveButton.value = text.length > 1
        joke = text
    }

    fun onDestroyView() {
        jokeText.value = joke
    }

    companion object {
        private const val JOKE_TEXT_KEY = "JOKE_TEXT_KEY"
    }
}