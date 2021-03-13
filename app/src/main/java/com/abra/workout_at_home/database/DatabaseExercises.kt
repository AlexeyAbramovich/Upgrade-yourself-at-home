package com.abra.workout_at_home.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abra.workout_at_home.data.ExercisesData

@Database(entities = [ExercisesData::class], version = 1, exportSchema = false)
abstract class DatabaseExercises : RoomDatabase() {
    abstract fun getExercisesDataDao(): ExercisesDataDao

    companion object {
        private var instance: DatabaseExercises? = null
        fun getDatabase(context: Context): DatabaseExercises {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    DatabaseExercises::class.java,
                    "database_exercises"
                )
                    .createFromAsset("database/database_workouts_ru.db")
                    .build()
            }
            return instance as DatabaseExercises
        }
    }
}