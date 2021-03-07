package com.abra.workout_at_home.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_info")
class UserInfo(
    @ColumnInfo val name: String,
    @ColumnInfo val weight: Int,
    @ColumnInfo val sex: String,
    @ColumnInfo val height: Int,
    @ColumnInfo val age: Int,
    @ColumnInfo val level: Int,
    @ColumnInfo val rang: String,
    @ColumnInfo val workouts: Int,
    @ColumnInfo val kkals: Int,
    @ColumnInfo val rest_time: Long,
    @ColumnInfo val ratio: Double,
    @ColumnInfo val exp: Int,
    @PrimaryKey @ColumnInfo var id: Int
)