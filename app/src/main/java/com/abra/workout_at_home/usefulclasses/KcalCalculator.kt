package com.abra.workout_at_home.usefulclasses

import kotlin.math.roundToInt

class KcalCalculator(
    private var totalTime: Int,
    private var factor: Double,
    private var userWeight: Int
) {
    private var kcals: Int = (factor * userWeight * totalTime).roundToInt()

    fun getKcals() = kcals
}