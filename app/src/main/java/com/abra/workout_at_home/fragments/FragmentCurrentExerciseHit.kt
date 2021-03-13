package com.abra.workout_at_home.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.abra.workout_at_home.R
import com.abra.workout_at_home.data.ExercisesData
import com.abra.workout_at_home.data.UserInfo
import com.abra.workout_at_home.databinding.FragmentCurrentExerciseBinding
import com.abra.workout_at_home.functions.getCorrectedTimeImMillis
import com.abra.workout_at_home.usefulclasses.TimerHit
import com.abra.workout_at_home.viewmodels.UserInfoViewModel
import com.abra.workout_at_home.viewmodels.UserInfoViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class FragmentCurrentExerciseHit : Fragment() {
    private lateinit var currentUserInfo: UserInfo
    private lateinit var viewModel: UserInfoViewModel
    private lateinit var timer: TimerHit
    private var startTime = 0L
    private lateinit var loader: FragmentLoader
    private lateinit var currentListOfExercises: List<ExercisesData>
    private var binding: FragmentCurrentExerciseBinding? = null
    private var difficulty: Int = 1
    private var totalTime: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurrentExerciseBinding.bind(
            inflater.inflate(
                R.layout.fragment_current_exercise,
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
        ).get(UserInfoViewModel::class.java)
        loader = requireActivity() as FragmentLoader
        loadData()
        getData(requireArguments())
        initTimer()
        setButtonsListeners()
    }

    private fun loadData() {
        viewModel.userInfoLiveData.observe(viewLifecycleOwner, { info ->
            currentUserInfo = info
        })
        viewModel.requestUserInfo(1)
    }

    private fun getData(bundle: Bundle) {
        currentListOfExercises = bundle.getParcelableArrayList("list")!!
        startTime = getCorrectedTimeImMillis(bundle.getLong("workTime", 0))
        difficulty = bundle.getInt("difficulty")
        totalTime = bundle.getInt("totalTime")
    }

    private fun initTimer() {
        binding?.run {
            timer = TimerHit(
                currentListOfExercises,
                startTime,
                difficulty,
                totalTime,
                tvTimer,
                ivCurrentExercise,
                buttonStart,
                tvCurrentExerciseName,
                tvNextExercise,
                tvTime,
                loader,
                context as Context
            )
        }
    }

    private fun setButtonsListeners() {
        binding?.run {
            buttonStart.setOnClickListener {
                if (timer.isTimerRunning() || timer.isTimerRestRunning()) pauseTimer()
                else startTimer()
            }
            buttonBack.setOnClickListener {
                createDialog()
            }
        }
    }

    private fun startTimer() {
        with(timer) {
            if (!isTimerRestRunning() && !isTimerRunning() && wasRestTimerRun())
                startRestTimer()
            else startTimer()
        }
    }

    private fun pauseTimer() {
        with(timer) {
            if (wasTimerCreated()) {
                if (isTimerRestRunning()) pauseRestTimer()
                else pauseTimer()
            }
        }
    }

    private fun createDialog() {
        pauseTimer()
        AlertDialog.Builder(context)
            .setMessage(getString(R.string.back_message))
            .setPositiveButton(
                getString(R.string.apply)
            ) { _, _ ->
                when (currentUserInfo.sex) {
                    "мужской" -> loader.loadFragment(FragmentWorkoutsMale())
                    "женский" -> loader.loadFragment(FragmentWorkoutsFemale())
                }
            }
            .setNegativeButton(getString(R.string.cancel)) { dialogInterface, _ -> dialogInterface.cancel() }
            .setCancelable(false)
            .create()
            .show()
    }

    override fun onStop() {
        super.onStop()
        with(timer) {
            if (wasTimerCreated()) {
                if (isTimerRestRunning()) pauseRestTimer()
                else pauseTimer()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        timer.releaseSoundPlayer()
    }
}