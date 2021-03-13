package com.abra.workout_at_home.repositories

import android.content.Context
import com.abra.workout_at_home.database.DatabaseExercises
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatabaseExercisesRepository(private val scope: CoroutineScope) {
    companion object {
        private lateinit var database: DatabaseExercises
        fun initDatabase(context: Context) {
            database = DatabaseExercises.getDatabase(context)
        }
    }

    suspend fun getExercisesByDifficulty(difficulty: String) =
        withContext(scope.coroutineContext + Dispatchers.IO) {
            database.getExercisesDataDao().getExercisesByDifficulty(difficulty)
        }
}