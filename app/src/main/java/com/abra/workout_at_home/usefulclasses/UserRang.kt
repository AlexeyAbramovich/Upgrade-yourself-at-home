package com.abra.workout_at_home.usefulclasses

class UserRang(
    private var currentLevel: Int,
    private var rang: String,
    private var ratio: Double
) {
    init {
        if (currentLevel in 1..9) {
            rang = "Новичек"
            ratio = 1.0
        } else {
            if (currentLevel in 10..29) {
                rang = "Любитель"
                ratio = 1.2
            } else {
                if (currentLevel in 30..49) {
                    rang = "Бывалый"
                    ratio = 1.4
                } else {
                    if (currentLevel in 50..69) {
                        rang = "Профессионал"
                        ratio = 1.6
                    } else {
                        if (currentLevel in 70..89) {
                            rang = "Асс"
                            ratio = 2.0
                        } else {
                            rang = "Легенда"
                            ratio = 10.0
                        }
                    }
                }
            }
        }
    }

    fun getUserRang() = rang

    fun getUserRatio() = ratio
}