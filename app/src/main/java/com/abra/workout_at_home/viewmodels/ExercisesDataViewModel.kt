package com.abra.workout_at_home.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abra.workout_at_home.data.ExercisesData
import com.abra.workout_at_home.repositories.DatabaseExercisesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ExercisesDataViewModel(private val scope: CoroutineScope) : ViewModel() {
    private val repository = DatabaseExercisesRepository(scope)
    private val mutableListOfExercisesLiveData = MutableLiveData<List<ExercisesData>>()
    val listOfExercisesLiveData: LiveData<List<ExercisesData>> = mutableListOfExercisesLiveData
    fun requestExercisesByDifficulty(difficulty: String) {
        scope.launch {
            mutableListOfExercisesLiveData.value = repository.getExercisesByDifficulty(difficulty)
        }
    }
}