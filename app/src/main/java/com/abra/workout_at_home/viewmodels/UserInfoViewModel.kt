package com.abra.workout_at_home.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abra.workout_at_home.data.UserInfo
import com.abra.workout_at_home.repositories.DatabaseUserInfoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class UserInfoViewModel(private val scope: CoroutineScope) : ViewModel() {
    private val repository = DatabaseUserInfoRepository(scope)
    private val mutableUserInfoLiveData = MutableLiveData<UserInfo>()
    val userInfoLiveData: LiveData<UserInfo> = mutableUserInfoLiveData
    fun requestUserInfo(id: Int) {
        scope.launch {
            mutableUserInfoLiveData.value = repository.getUserInfoById(id)
        }
    }

    fun addUserInfo(info: UserInfo) {
        scope.launch {
            repository.addUserInfo(info)
        }
    }

    fun updateUserInfo(info: UserInfo) {
        scope.launch {
            repository.updateUserInfo(info)
        }
    }

    fun deleteUserInfo(info: UserInfo) {
        scope.launch {
            repository.deleteUserInfo(info)
        }
    }
}