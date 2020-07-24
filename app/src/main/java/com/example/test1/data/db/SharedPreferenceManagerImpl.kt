package com.example.test1.data.db

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceManagerImpl(private val context: Context) : SharedPreferenceManager() {

    private fun getSharedPreferences(prefsName: String): SharedPreferences {
        return context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
    }

    override fun saveFirstName(firstName: String) {
        getSharedPreferences(FIRST_NAME_PREFS_NAME)
            .edit()
            .putString(FIRST_NAME_PREFS_KEY, firstName)
            .apply()
    }

    override fun saveLastName(lastName: String) {
        getSharedPreferences(LAST_NAME_PREFS_NAME)
            .edit()
            .putString(LAST_NAME_PREFS_KEY, lastName)
            .apply()
    }

    override fun getFirstName(): String {
        return getSharedPreferences(FIRST_NAME_PREFS_NAME)
            .getString(FIRST_NAME_PREFS_KEY, "") ?: ""
    }

    override fun getLastName(): String {
        return getSharedPreferences(LAST_NAME_PREFS_NAME)
            .getString(LAST_NAME_PREFS_KEY, "") ?: ""
    }

    override fun saveIsOfflineMode(IsOfflineMode: Boolean) {
        getSharedPreferences(OFFLINE_MODE_PREFS_NAME)
            .edit()
            .putBoolean(OFFLINE_MODE_PREFS_KEY, IsOfflineMode)
            .apply()
    }

    override fun getIsOfflineMode(): Boolean {
        return getSharedPreferences(OFFLINE_MODE_PREFS_NAME)
            .getBoolean(OFFLINE_MODE_PREFS_KEY, false)
    }

    companion object {
        const val FIRST_NAME_PREFS_NAME = "FirstNamePrefsName"
        const val FIRST_NAME_PREFS_KEY = "FirstNamePrefsNameKey"
        const val LAST_NAME_PREFS_NAME = "LastNamePrefsName"
        const val LAST_NAME_PREFS_KEY = "LastNamePrefsKey"
        const val OFFLINE_MODE_PREFS_NAME = "OfflineModePrefsName"
        const val OFFLINE_MODE_PREFS_KEY = "OfflineModePrefsKey"
    }
}
