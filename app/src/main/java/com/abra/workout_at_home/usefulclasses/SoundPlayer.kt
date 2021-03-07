package com.abra.workout_at_home.usefulclasses

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import com.abra.workout_at_home.R

class SoundPlayer(private val context: Context) {
    private var soundPool: SoundPool? = null
    private var sound = 0
    fun prepareSoundPlayer() {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        soundPool = SoundPool.Builder()
            .setMaxStreams(1)
            .setAudioAttributes(audioAttributes)
            .build()
        sound = soundPool?.load(context, R.raw.exercise_complete, 1) as Int
    }

    fun playSound() {
        soundPool?.play(sound, 1F, 1F, 0, 0, 1F)
    }

    fun releaseSoundPlayer() {
        soundPool?.release()
        soundPool = null
    }
}