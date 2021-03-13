package com.abra.workout_at_home.usefulclasses

import android.content.Context
import android.net.Uri
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.abra.workout_at_home.R
import com.abra.workout_at_home.data.ExercisesData
import com.abra.workout_at_home.fragments.DialogFragmentEndOfWorkout
import com.abra.workout_at_home.fragments.FragmentLoader
import com.abra.workout_at_home.functions.getTimeFormattedString
import com.bumptech.glide.Glide
import pl.droidsonroids.gif.GifImageView

class Timer(
    private val currentListOfExercises: List<ExercisesData>,
    private val startTime: Long,
    private var restTime: Long,
    private val difficulty: Int,
    private var totalTime: Int,
    private val tvTimer: TextView,
    private val ivExercise: GifImageView,
    private val buttonStart: ImageButton,
    private val tvCurrentExerciseName: TextView,
    private val tvNextExercise: TextView,
    private val tvTime: TextView,
    private val loader: FragmentLoader,
    private val context: Context
) {
    private lateinit var timer: CountDownTimer
    private lateinit var restTimer: CountDownTimer
    private var timerRunning = false
    private var timerRestRunning = false
    private var timeLeftInMillis = startTime
    private var timeRestLeftInMillis = restTime
    private var currentExercisePosition = 0
    private val soundPlayer = SoundPlayer(context)
    private var wasRestTimerRun = false
    private var timerWasCreated = false

    init {
        tvCurrentExerciseName.text = currentListOfExercises[currentExercisePosition].name
        Glide.with(context).load(Uri.parse(currentListOfExercises[currentExercisePosition].path))
            .into(ivExercise)
        soundPlayer.prepareSoundPlayer()
        updateTextTimer(startTime)
    }

    fun startTimer() {
        timerWasCreated = true
        timer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateTextTimer(timeLeftInMillis)
            }

            override fun onFinish() {
                updateTextTimer(timeLeftInMillis)
                timeLeftInMillis = startTime
                timerRunning = false
                buttonStart.setImageResource(R.drawable.ic_baseline_play_arrow_64)
                currentExercisePosition++
                if (currentExercisePosition != currentListOfExercises.size) {
                    if (restTime == 0L) {
                        soundPlayer.playSound()
                        startTimer()
                        tvCurrentExerciseName.text =
                            currentListOfExercises[currentExercisePosition].name
                        Glide.with(context)
                            .load(Uri.parse(currentListOfExercises[currentExercisePosition].path))
                            .into(ivExercise)
                    } else {
                        soundPlayer.playSound()
                        startRestTimer()
                    }
                } else {
                    loader.loadDialogFragment(DialogFragmentEndOfWorkout(difficulty, totalTime))
                }
            }

        }.start()
        timerRunning = true
        buttonStart.setImageResource(R.drawable.ic_baseline_pause_64)
    }

    fun pauseTimer() {
        timer.cancel()
        timerRunning = false
        buttonStart.setImageResource(R.drawable.ic_baseline_play_arrow_64)
    }

    private fun updateTextTimer(time: Long) {
        tvTimer.text = getTimeFormattedString(time)
    }

    fun isTimerRunning() = timerRunning

    fun isTimerRestRunning() = timerRestRunning

    fun wasRestTimerRun() = wasRestTimerRun

    fun wasTimerCreated() = timerWasCreated

    fun startRestTimer() {
        wasRestTimerRun = true
        updateTextTimer(timeRestLeftInMillis)
        tvNextExercise.visibility = View.VISIBLE
        tvTime.text = context.resources.getString(R.string.rest)
        tvCurrentExerciseName.text =
            currentListOfExercises[currentExercisePosition].name
        Glide.with(context).load(Uri.parse(currentListOfExercises[currentExercisePosition].path))
            .into(ivExercise)
        restTimer = object : CountDownTimer(timeRestLeftInMillis, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                timeRestLeftInMillis = millisUntilFinished
                updateTextTimer(timeRestLeftInMillis)
            }

            override fun onFinish() {
                updateTextTimer(timeRestLeftInMillis)
                timeRestLeftInMillis = restTime
                soundPlayer.playSound()
                tvNextExercise.visibility = View.INVISIBLE
                tvTime.text = context.resources.getString(R.string.time)
                timerRestRunning = false
                wasRestTimerRun = false
                startTimer()
            }
        }.start()
        timerRestRunning = true
        buttonStart.setImageResource(R.drawable.ic_baseline_pause_64)
    }

    fun pauseRestTimer() {
        restTimer.cancel()
        timerRestRunning = false
        buttonStart.setImageResource(R.drawable.ic_baseline_play_arrow_64)
    }

    fun updateRestTime(time: Long) {
        restTime = time
        timeRestLeftInMillis = restTime
    }

    fun releaseSoundPlayer() {
        soundPlayer.releaseSoundPlayer()
    }
}