package com.example.test1.ui.myjokes

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.test1.core.SingleLiveEvent

class JokeSharedViewModel : ViewModel() {
    companion object {
        private const val TAG = "JokeSharedViewModel"
        fun obtainFrom(activity: FragmentActivity): JokeSharedViewModel {
            return ViewModelProvider(
                activity,
                ViewModelProvider.AndroidViewModelFactory.getInstance(activity.application)
            ).get(TAG, JokeSharedViewModel::class.java)
        }
    }

    val isAddedJoke = SingleLiveEvent<Unit>()
    fun getIsAddedJoke() = isAddedJoke

}
