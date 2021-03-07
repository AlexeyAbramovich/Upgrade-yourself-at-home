package com.abra.workout_at_home.usefulclasses

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.abra.workout_at_home.R

class UserAchievements(
    private val workouts: Int,
    private val arrayOfAchievements: Array<ImageView>,
    private val arrayOfTextAchievements: Array<TextView>,
    private val context: Context
) {
    private var achievementsNumber = 0
    private val achievementsState = arrayOf(false, false, false, false, false, false)
    private val activeStateForeground = arrayOf(
        R.drawable.workouts_5,
        R.drawable.workouts_10,
        R.drawable.workouts_30,
        R.drawable.workouts_60,
        R.drawable.workouts_100,
        R.drawable.workouts_150
    )
    private val activeStateBackground = arrayOf(
        R.drawable.workouts_5_active_shape,
        R.drawable.workouts_10_active_shape,
        R.drawable.workouts_30_active_shape,
        R.drawable.workouts_60_active_shape,
        R.drawable.workouts_100_active_shape,
        R.drawable.workouts_150_active_shape
    )

    init {
        checkAchievementsState()
    }

    private fun checkAchievementsState() {
        if (workouts >= 5) {
            achievementsState[0] = true
            achievementsNumber++
        }
        if (workouts >= 10) {
            achievementsState[1] = true
            achievementsNumber++
        }
        if (workouts >= 30) {
            achievementsState[2] = true
            achievementsNumber++
        }
        if (workouts >= 60) {
            achievementsState[3] = true
            achievementsNumber++
        }
        if (workouts >= 100) {
            achievementsState[4] = true
            achievementsNumber++
        }
        if (workouts >= 150) {
            achievementsState[5] = true
            achievementsNumber++
        }
    }

    fun setAchievementsState() {
        arrayOfAchievements.forEach {
            if (achievementsState[arrayOfAchievements.indexOf(it)]) {
                it.setImageResource(activeStateForeground[arrayOfAchievements.indexOf(it)])
                it.setBackgroundResource(activeStateBackground[arrayOfAchievements.indexOf(it)])
                arrayOfTextAchievements[arrayOfAchievements.indexOf(it)].setTextColor(
                    context.getColor(
                        R.color.main_text_color
                    )
                )
            }
        }
    }
    fun getAchievementsNumber() = achievementsNumber
}