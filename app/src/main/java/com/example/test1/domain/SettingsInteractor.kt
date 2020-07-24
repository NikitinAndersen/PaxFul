package com.example.test1.domain

interface SettingsInteractor {

    suspend fun getIsOfflineMode(): Boolean

    suspend fun saveIsOfflineMode(isOfflineMode: Boolean)

    suspend fun getLastName(): String

    suspend fun getFirstName(): String

    suspend fun saveFirstName(firstName: String)

    suspend fun saveLastName(lastName: String)
}