package com.abra.workout_at_home.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import java.lang.IllegalArgumentException

class ExercisesDataViewModelFactory(private val scope: CoroutineScope) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExercisesDataViewModel::class.java)) {
            return ExercisesDataViewModel(
                scope = scope
            ) as T
        }
        throw IllegalArgumentException("Unknown class for the requested ViewModel")
    }
}