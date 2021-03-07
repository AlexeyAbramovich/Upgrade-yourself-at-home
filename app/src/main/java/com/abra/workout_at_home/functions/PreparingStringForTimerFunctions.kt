package com.abra.workout_at_home.functions

import java.util.Locale

fun getCorrectedTimeImMillis(time: Long) = time * 1000 + 200
fun getTimeFormattedString(time: Long): String {
    val minutes = (time / 1000 / 60).toInt()
    val seconds = (time / 1000 % 60).toInt()
    return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
}