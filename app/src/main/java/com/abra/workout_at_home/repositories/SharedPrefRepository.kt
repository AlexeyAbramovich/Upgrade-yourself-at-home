package com.abra.workout_at_home.repositories

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

class SharedPrefRepository(private val context: Context) {
    private val pref = context.getSharedPreferences("loadingStatus", AppCompatActivity.MODE_PRIVATE)
    fun setLoadingStatus(status: Boolean) {
        pref.edit().putBoolean("userInfoWasFilled", status).apply()
    }

    fun getLoadingStatus() = pref.getBoolean("userInfoWasFilled", false)
}