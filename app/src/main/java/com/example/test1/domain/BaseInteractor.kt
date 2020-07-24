package com.example.test1.domain

import com.example.test1.data.db.SharedPreferenceManager

abstract class BaseInteractor(
    private val sharedPrefs: SharedPreferenceManager
) {

    fun isNeedToChangeName(): Boolean {
        val firstName = sharedPrefs.getFirstName()
        val lastName = sharedPrefs.getLastName()
        if (firstName.isBlank() && lastName.isBlank()) {
            return false
        }
        return true
    }
}