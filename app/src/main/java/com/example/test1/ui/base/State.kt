package com.example.test1.ui.base

sealed class StateAwareResponse<T> {
    class Loaded<T>(val data: T) : StateAwareResponse<T>()
    class Error<T>(val throwable: Throwable) : StateAwareResponse<T>()
}

val <T> T.loadedState: StateAwareResponse.Loaded<T>
    get() = StateAwareResponse.Loaded(this)
