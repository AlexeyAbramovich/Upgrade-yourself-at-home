package com.abra.workout_at_home.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.abra.workout_at_home.R
import com.abra.workout_at_home.data.UserInfo
import com.abra.workout_at_home.databinding.FragmentEndOfWorkoutBinding
import com.abra.workout_at_home.usefulclasses.KcalCalculator
import com.abra.workout_at_home.usefulclasses.UserLevel
import com.abra.workout_at_home.usefulclasses.WorkoutDifficulty
import com.abra.workout_at_home.viewmodels.UserInfoViewModel
import com.abra.workout_at_home.viewmodels.UserInfoViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job


class DialogFragmentEndOfWorkout(private val difficulty: Int, private val totalTime: Int) :
    DialogFragment() {
    private var exp = 0
    private lateinit var viewModel: UserInfoViewModel
    private lateinit var loader: FragmentLoader
    private var binding: FragmentEndOfWorkoutBinding? = null
    private lateinit var currentUserInfo: UserInfo
    private lateinit var calculator: KcalCalculator
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEndOfWorkoutBinding.bind(
            inflater.inflate(
                R.layout.fragment_end_of_workout,
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
        isCancelable = false
        loader = requireActivity() as FragmentLoader
        getUserInfo()
        setButtonListener()
    }

    private fun setButtonListener() {
        binding?.buttonContinue?.setOnClickListener {
            updateUserInfo()
            when (currentUserInfo.sex) {
                "мужской" -> loader.loadFragment(FragmentWorkoutsMale())
                "женский" -> loader.loadFragment(FragmentWorkoutsFemale())
            }
            dismiss()
        }
    }

    private fun updateUserInfo() {
        val userLevel = UserLevel(
            currentUserInfo.level,
            currentUserInfo.exp,
            exp,
            currentUserInfo.rang,
            currentUserInfo.ratio
        )
        viewModel.updateUserInfo(
            UserInfo(
                name = currentUserInfo.name,
                weight = currentUserInfo.weight,
                sex = currentUserInfo.sex,
                height = currentUserInfo.height,
                age = currentUserInfo.age,
                userLevel.getLevel(),
                userLevel.getUserRang(),
                currentUserInfo.workouts + 1,
                currentUserInfo.kkals + calculator.getKcals(),
                currentUserInfo.rest_time,
                userLevel.getUserRatio(),
                userLevel.getExp(),
                currentUserInfo.id
            )
        )
    }

    private fun getUserInfo() {
        viewModel.userInfoLiveData.observe(viewLifecycleOwner, { info ->
            currentUserInfo = info
            setDataIntoViews(info)
        })
        viewModel.requestUserInfo(1)
    }

    @SuppressLint("SetTextI18n")
    private fun setDataIntoViews(info: UserInfo) {
        val workoutDifficulty = WorkoutDifficulty(difficulty, info.ratio)
        exp = workoutDifficulty.getExp()
        calculator = KcalCalculator(totalTime, 0.13, info.weight)
        binding?.run {
            tvKkals.text = calculator.getKcals().toString()
            tvExp.text = "+$exp"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}