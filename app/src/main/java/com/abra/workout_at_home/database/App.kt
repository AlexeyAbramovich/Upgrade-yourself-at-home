package com.abra.workout_at_home.database

import android.app.Application
import com.abra.workout_at_home.repositories.DatabaseExercisesRepository
import com.abra.workout_at_home.repositories.DatabaseUserInfoRepository

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        DatabaseUserInfoRepository.initDatabase(this)
        DatabaseExercisesRepository.initDatabase(this)
    }
}