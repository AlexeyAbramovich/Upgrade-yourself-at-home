package com.abra.workout_at_home.repositories

import android.content.Context
import com.abra.workout_at_home.data.UserInfo
import com.abra.workout_at_home.database.DatabaseUserInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatabaseUserInfoRepository(private val scope: CoroutineScope) {
    companion object {
        private lateinit var database: DatabaseUserInfo
        fun initDatabase(context: Context) {
            database = DatabaseUserInfo.getDatabase(context)
        }
    }

    suspend fun getUserInfoById(id: Int) =
        withContext(scope.coroutineContext + Dispatchers.IO) {
            database.getUserInfoDao().getUserInfoById(id)
        }

    suspend fun addUserInfo(info: UserInfo) {
        withContext(scope.coroutineContext + Dispatchers.IO) {
            database.getUserInfoDao().addUserInfo(info)
        }
    }

    suspend fun updateUserInfo(info: UserInfo) {
        withContext(scope.coroutineContext + Dispatchers.IO) {
            database.getUserInfoDao().updateUserInfo(info)
        }
    }

    suspend fun deleteUserInfo(info: UserInfo) {
        withContext(scope.coroutineContext + Dispatchers.IO) {
            database.getUserInfoDao().deleteUserInfo(info)
        }
    }
}