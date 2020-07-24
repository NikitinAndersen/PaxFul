package com.example.test1.data.db

abstract class SharedPreferenceManager {

    abstract fun saveFirstName(firstName: String)
    abstract fun getFirstName(): String

    abstract fun saveLastName(lastName: String)
    abstract fun getLastName(): String

    abstract fun saveIsOfflineMode(IsOfflineMode: Boolean)
    abstract fun getIsOfflineMode(): Boolean
}