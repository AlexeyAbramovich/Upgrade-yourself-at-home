package com.abra.workout_at_home.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abra.workout_at_home.data.UserInfo

@Database(entities = [UserInfo::class], version = 1, exportSchema = false)
abstract class DatabaseUserInfo : RoomDatabase() {
    abstract fun getUserInfoDao(): UserInfoDao

    companion object {
        private var instance: DatabaseUserInfo? = null
        fun getDatabase(context: Context): DatabaseUserInfo {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    DatabaseUserInfo::class.java,
                    "database_user_info"
                )
                    .build()
            }
            return instance as DatabaseUserInfo
        }
    }
}