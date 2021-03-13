package com.abra.workout_at_home.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.abra.workout_at_home.R
import com.abra.workout_at_home.data.UserInfo
import com.abra.workout_at_home.databinding.ActivityUserInfoBinding
import com.abra.workout_at_home.repositories.SharedPrefRepository
import com.abra.workout_at_home.viewmodels.UserInfoViewModel
import com.abra.workout_at_home.viewmodels.UserInfoViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.util.Locale

class UserInfoActivity : AppCompatActivity() {
    private lateinit var viewModel: UserInfoViewModel
    private lateinit var repository: SharedPrefRepository
    private var binding: ActivityUserInfoBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserInfoBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        viewModel = ViewModelProvider(
            this,
            UserInfoViewModelFactory(CoroutineScope(Dispatchers.Main + Job()))
        )
            .get(UserInfoViewModel::class.java)
        repository = SharedPrefRepository(this)
        setSpinnerDesign()
        setButtonListener()
    }

    private fun setSpinnerDesign() {
        binding?.run {
            with(spinnerSex) {
                adapter = ArrayAdapter.createFromResource(
                    this@UserInfoActivity,
                    R.array.sex,
                    R.layout.colored_spinner_layout
                ).also {
                    it.setDropDownViewResource(R.layout.spinner_dropdown_layout)
                }
            }
        }
    }

    private fun setButtonListener() {
        binding?.buttonContinue?.setOnClickListener {
            getData()
        }
    }

    private fun getData() {
        binding?.run {
            val name = etUserName.text.toString()
            val textWeight = etUserWeight.text.toString()
            val textAge = etUserAge.text.toString()
            val textHeight = etUserHeight.text.toString()
            val sex = getDataFromSpinner()
            if (name.isNotEmpty() && textWeight.isNotEmpty() &&
                textAge.isNotEmpty() && textHeight.isNotEmpty()
            ) {
                if (textWeight.toInt() <= 150 && textHeight.toInt() <= 250 && textAge.toInt() <= 80) {
                    viewModel.addUserInfo(
                        UserInfo(
                            name = name,
                            weight = textWeight.toInt(),
                            sex = sex,
                            height = textHeight.toInt(),
                            age = textAge.toInt(),
                            level = 1,
                            rang = "Новичек",
                            workouts = 0,
                            kkals = 0,
                            rest_time = 0,
                            ratio = 1.0,
                            exp = 0,
                            id = 1
                        )
                    )
                    setLoadingStatus()
                    showActivity()
                } else {
                    Snackbar.make(
                        binding?.root as View,
                        "Введите реальные значения!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            } else Snackbar.make(
                binding?.root as View,
                "Заполните все поля!",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun showActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun setLoadingStatus() {
        repository.setLoadingStatus(true)
    }

    private fun getDataFromSpinner(): String {
        var sex = "мужской"
        binding?.run {
            sex = (spinnerSex.selectedItem as String).toLowerCase(Locale.ROOT)
        }
        return sex
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onBackPressed() {}
}