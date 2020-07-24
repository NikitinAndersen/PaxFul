package com.example.test1.domain.interactors

import com.example.test1.data.db.SharedPreferenceManager
import com.example.test1.domain.SettingsInteractor

class SettingsInteractorImpl(private val sharedPreferenceManager: SharedPreferenceManager) :
    SettingsInteractor {

    override suspend fun saveIsOfflineMode(isOfflineMode: Boolean) {
        sharedPreferenceManager.saveIsOfflineMode(isOfflineMode)
    }

    override suspend fun saveFirstName(firstName: String) {
        sharedPreferenceManager.saveFirstName(firstName)
    }

    override suspend fun saveLastName(lastName: String) {
        sharedPreferenceManager.saveLastName(lastName)
    }

    override suspend fun getLastName(): String {
        return sharedPreferenceManager.getLastName()
    }

    override suspend fun getFirstName(): String {
        return sharedPreferenceManager.getFirstName()
    }

    override suspend fun getIsOfflineMode(): Boolean {
        return sharedPreferenceManager.getIsOfflineMode()
    }
}