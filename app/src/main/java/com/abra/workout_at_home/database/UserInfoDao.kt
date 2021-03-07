package com.abra.workout_at_home.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.abra.workout_at_home.data.UserInfo

@Dao
interface UserInfoDao {
    @Query("SELECT * FROM user_info WHERE id = :id")
    fun getUserInfoById(id: Int): UserInfo

    @Insert
    fun addUserInfo(info: UserInfo)

    @Delete
    fun deleteUserInfo(info: UserInfo)

    @Update
    fun updateUserInfo(info: UserInfo)
}