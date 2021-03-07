package com.abra.workout_at_home.database

import androidx.room.Dao
import androidx.room.Query
import com.abra.workout_at_home.data.ExercisesData

@Dao
interface ExercisesDataDao {
    @Query("SELECT * FROM exercises WHERE difficulty = :difficulty")
    fun getExercisesByDifficulty(difficulty: String): List<ExercisesData>
}