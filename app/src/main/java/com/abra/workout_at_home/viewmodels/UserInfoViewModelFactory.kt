package com.abra.workout_at_home.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import java.lang.IllegalArgumentException

class UserInfoViewModelFactory(private val scope: CoroutineScope) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserInfoViewModel::class.java)) {
            return UserInfoViewModel(
                scope = scope
            ) as T
        }
        throw IllegalArgumentException("Unknown class for the requested ViewModel")
    }
}