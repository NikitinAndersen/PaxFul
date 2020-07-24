package com.example.test1.ui.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.test1.domain.SettingsInteractor
import com.example.test1.extensions.asImmutable
import com.example.test1.ui.base.BaseViewModel

class SettingsViewModel(
    savedStateHandle: SavedStateHandle,
    private val interactor: SettingsInteractor
) :
    BaseViewModel() {

    private val isOfflineModeEnabled: MutableLiveData<Boolean> =
        savedStateHandle.getLiveData(IS_OFFLINE_MODE_KEY, false)
    private val firstName: MutableLiveData<String> =
        savedStateHandle.getLiveData(FIRST_NAME_KEY, "")
    private val lastName: MutableLiveData<String> =
        savedStateHandle.getLiveData(LAST_NAME_KEY, "")

    fun getIsOfflineModeEnabled() = isOfflineModeEnabled.asImmutable()
    fun getFirstName() = firstName.asImmutable()
    fun getLastName() = lastName.asImmutable()

    init {
        loadData()
    }

    private fun loadData() {
        runCoroutine {
            firstName.value = interactor.getFirstName()
            lastName.value = interactor.getLastName()
            isOfflineModeEnabled.value = interactor.getIsOfflineMode()
        }
    }

    fun offlineModeChecked(checked: Boolean) {
        runCoroutine {
            interactor.saveIsOfflineMode(checked)
        }
    }

    fun firstNameChanged(firstName: String) {
        runCoroutine {
            interactor.saveFirstName(firstName)
        }
    }

    fun lastNameChanged(lastName: String) {
        runCoroutine {
            interactor.saveLastName(lastName)
        }
    }

    companion object {
        private const val FIRST_NAME_KEY = "FIRST_NAME_KEY"
        private const val LAST_NAME_KEY = "LAST_NAME_KEY"
        private const val IS_OFFLINE_MODE_KEY = "IS_OFFLINE_MODE_KEY"
    }

}