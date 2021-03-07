package com.abra.workout_at_home.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.abra.workout_at_home.R
import com.abra.workout_at_home.activities.SplashScreenActivity
import com.abra.workout_at_home.data.UserInfo
import com.abra.workout_at_home.databinding.FragmentSettingsBinding
import com.abra.workout_at_home.repositories.SharedPrefRepository
import com.abra.workout_at_home.viewmodels.UserInfoViewModel
import com.abra.workout_at_home.viewmodels.UserInfoViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.util.Locale


class FragmentSettings : Fragment() {
    private lateinit var viewModel: UserInfoViewModel
    private var binding: FragmentSettingsBinding? = null
    private lateinit var currentUserInfo: UserInfo
    private lateinit var repository: SharedPrefRepository
    private lateinit var loader: FragmentLoader
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.bind(
            inflater.inflate(
                R.layout.fragment_settings,
                container,
                false
            )
        )
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            UserInfoViewModelFactory(CoroutineScope(Dispatchers.Main + Job()))
        )
            .get(UserInfoViewModel::class.java)
        repository = SharedPrefRepository(context as Context)
        loader = requireActivity() as FragmentLoader
        setSpinnersDesign()
        loadData()
        setButtonsListeners()
    }

    private fun setSpinnersDesign() {
        binding?.run {
            with(spinnerSex) {
                adapter = ArrayAdapter.createFromResource(
                    context,
                    R.array.sex,
                    R.layout.colored_spinner_settings_layout
                ).also {
                    it.setDropDownViewResource(R.layout.spinner_dropdown_settings_layout)
                }
            }
            with(spinnerRestTime) {
                adapter = ArrayAdapter.createFromResource(
                    context,
                    R.array.res_time,
                    R.layout.colored_spinner_settings_layout
                ).also {
                    it.setDropDownViewResource(R.layout.spinner_dropdown_settings_layout)
                }
            }
        }
    }

    private fun loadData() {
        viewModel.userInfoLiveData.observe(viewLifecycleOwner, {
            currentUserInfo = it
            setData(it)
        })
        viewModel.requestUserInfo(1)
    }

    private fun setData(info: UserInfo) {
        binding?.run {
            etUserName.setText(info.name)
            etUserWeight.setText(info.weight.toString())
            etUserHeight.setText(info.height.toString())
            etUserAge.setText(info.age.toString())
            when (info.sex) {
                "мужской" -> spinnerSex.setSelection(0)
                "женский" -> spinnerSex.setSelection(1)
            }
            when (info.rest_time) {
                0L -> spinnerRestTime.setSelection(0)
                10L -> spinnerRestTime.setSelection(1)
                20L -> spinnerRestTime.setSelection(2)
                30L -> spinnerRestTime.setSelection(3)
                40L -> spinnerRestTime.setSelection(4)
                60L -> spinnerRestTime.setSelection(5)
            }
        }
    }

    private fun setButtonsListeners() {
        binding?.run {
            buttonReset.setOnClickListener {
                createResetDialog()
            }
            buttonApply.setOnClickListener {
                updateUserInfo()
            }
        }
    }

    private fun createResetDialog() {
        AlertDialog.Builder(context)
            .setTitle(getString(R.string.reset_title))
            .setMessage(getString(R.string.reset_message))
            .setPositiveButton(
                getString(R.string.apply)
            ) { _, _ ->
                viewModel.deleteUserInfo(currentUserInfo)
                repository.setLoadingStatus(false)
                startActivity(Intent(context, SplashScreenActivity::class.java))
            }
            .setNegativeButton(getString(R.string.cancel)) { dialogInterface, _ -> dialogInterface.cancel() }
            .setCancelable(false)
            .create()
            .show()
    }

    private fun updateUserInfo() {
        binding?.run {
            val name = etUserName.text.toString()
            val textWeight = etUserWeight.text.toString()
            val textAge = etUserAge.text.toString()
            val textHeight = etUserHeight.text.toString()
            val sex = getDataFromSpinnerSex()
            val time = getDataFromSpinnerRestTime()
            if (name.isNotEmpty() && textWeight.isNotEmpty() &&
                textAge.isNotEmpty() && textHeight.isNotEmpty()
            ) {
                if (textWeight.toInt() <= 150 && textHeight.toInt() <= 250 && textAge.toInt() <= 80) {
                    viewModel.updateUserInfo(
                        UserInfo(
                            name = name,
                            weight = textWeight.toInt(),
                            sex = sex,
                            height = textHeight.toInt(),
                            age = textAge.toInt(),
                            level = currentUserInfo.level,
                            rang = currentUserInfo.rang,
                            workouts = currentUserInfo.workouts,
                            kkals = currentUserInfo.kkals,
                            rest_time = time.toLong(),
                            ratio = currentUserInfo.ratio,
                            exp = currentUserInfo.exp,
                            id = currentUserInfo.id
                        )
                    )
                    loader.loadData()
                    loader.loadFragmentUserStatistic()
                    Snackbar.make(
                        binding?.root as View,
                        "Информация обновлена!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else {
                    Snackbar.make(
                        binding?.root as View,
                        "Введите реальные значения!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            } else Snackbar.make(
                binding?.root as View,
                "Fields can't be empty!",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun getDataFromSpinnerSex(): String {
        var sex = "мужской"
        binding?.run {
            sex = (spinnerSex.selectedItem as String).toLowerCase(Locale.ROOT)
        }
        return sex
    }

    private fun getDataFromSpinnerRestTime(): String {
        var time = "0 сек"
        binding?.run {
            time = (spinnerRestTime.selectedItem as String).toLowerCase(Locale.ROOT)
        }
        return time.split(" ")[0]
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}