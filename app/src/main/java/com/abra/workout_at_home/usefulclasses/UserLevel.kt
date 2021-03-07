package com.abra.workout_at_home.usefulclasses

class UserLevel(
    private var currentLevel: Int,
    private var currentExp: Int,
    private var exp: Int,
    private var rang: String,
    private var ratio: Double
) {
    private var userRang: UserRang

    init {
        val res = currentExp + exp
        currentExp = if (res >= 100) {
            currentLevel++
            res - 100
        } else {
            res
        }
        userRang = UserRang(currentLevel, rang, ratio)
    }

    fun getLevel() = currentLevel

    fun getExp() = currentExp

    fun getUserRang() = userRang.getUserRang()

    fun getUserRatio() = userRang.getUserRatio()
}