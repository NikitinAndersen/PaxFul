package com.example.test1.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test1.extensions.asImmutable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseViewModel : ViewModel() {
    protected val isLoading = MutableLiveData<Boolean>()
    private val isError = MutableLiveData<Throwable>()
    protected val isEmptyView = MutableLiveData<Boolean>()

    fun getIsLoading() = isLoading.asImmutable()
    fun getIsError() = isError.asImmutable()
    fun getIsShowEmptyView() = isEmptyView.asImmutable()

    protected fun showError(throwable: Throwable) {
        isError.value = throwable
    }

    protected inline fun <T> withProgress(block: () -> T): T {
        isLoading.value = true
        val result = block()
        isLoading.value = false
        return result
    }

    protected fun runCoroutine(
        context: CoroutineContext = EmptyCoroutineContext,
        block: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(context) { block() }
}