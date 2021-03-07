package com.abra.workout_at_home.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.ViewModelProvider
import com.abra.workout_at_home.R
import com.abra.workout_at_home.repositories.SharedPrefRepository
import com.abra.workout_at_home.viewmodels.UserInfoViewModel
import com.abra.workout_at_home.viewmodels.UserInfoViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var viewModel: UserInfoViewModel
    private lateinit var repository: SharedPrefRepository
    private var userInfoWasFilled: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            UserInfoViewModelFactory(CoroutineScope(Dispatchers.Main + Job()))
        )
            .get(UserInfoViewModel::class.java)
        repository = SharedPrefRepository(this)
        setLoadingScreen()
        runNextActivity()
    }

    private fun setLoadingScreen() {
        userInfoWasFilled = repository.getLoadingStatus()
        if (userInfoWasFilled) {
            viewModel.userInfoLiveData.observe(this, { data ->
                when (data.sex) {
                    "мужской" -> setContentView(R.layout.activity_loading_screen_male)
                    "женский" -> setContentView(R.layout.activity_loading_screen_female)
                }
            })
            viewModel.requestUserInfo(1)
        } else setContentView(R.layout.activity_loading_screen_male)
    }

    private fun runNextActivity() {
        Handler().postDelayed({
            if (userInfoWasFilled) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                startActivity(Intent(this, WelcomeActivity::class.java))
            }
        }, 2500L)
    }

    override fun onBackPressed() {}
}