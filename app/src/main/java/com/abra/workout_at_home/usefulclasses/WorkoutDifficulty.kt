package com.abra.workout_at_home.usefulclasses

import kotlin.math.roundToInt

class WorkoutDifficulty(
    private val difficulty: Int,
    private val ratio: Double
) {
    fun getExp(): Int {
        var exp = 0
        when (difficulty) {
            1 -> exp = (10 / ratio).roundToInt()
            2 -> exp = (20 / ratio).roundToInt()
            3 -> exp = (40 / ratio).roundToInt()
            4 -> exp = (60 / ratio).roundToInt()
            5 -> exp = (80 / ratio).roundToInt()
            6 -> exp = (100 / ratio).roundToInt()
        }
        return exp
    }
}