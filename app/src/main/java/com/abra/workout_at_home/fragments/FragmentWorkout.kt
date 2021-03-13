package com.abra.workout_at_home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.abra.workout_at_home.R
import com.abra.workout_at_home.activities.MainActivity
import com.abra.workout_at_home.adapters.ExercisesDataAdapter
import com.abra.workout_at_home.data.UserInfo
import com.abra.workout_at_home.databinding.FragmentWorkoutBinding
import com.abra.workout_at_home.viewmodels.ExercisesDataViewModel
import com.abra.workout_at_home.viewmodels.ExercisesDataViewModelFactory
import com.abra.workout_at_home.viewmodels.UserInfoViewModel
import com.abra.workout_at_home.viewmodels.UserInfoViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class FragmentWorkout : Fragment() {
    private lateinit var currentUserInfo: UserInfo
    private lateinit var viewModel: ExercisesDataViewModel
    private lateinit var viewModelInfo: UserInfoViewModel
    private lateinit var currentActivity: MainActivity
    private var binding: FragmentWorkoutBinding? = null
    private lateinit var adapter: ExercisesDataAdapter
    private var workTime = 0L
    private var difficulty: Int = 1
    private lateinit var difficultyType: String
    private var totalTime: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkoutBinding.bind(
            inflater.inflate(
                R.layout.fragment_workout,
                container,
                false
            )
        )
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentActivity = requireActivity() as MainActivity
        viewModel = ViewModelProvider(
            this,
            ExercisesDataViewModelFactory(CoroutineScope(Dispatchers.Main + Job()))
        ).get(ExercisesDataViewModel::class.java)
        viewModelInfo = ViewModelProvider(
            this,
            UserInfoViewModelFactory(CoroutineScope(Dispatchers.Main + Job()))
        ).get(UserInfoViewModel::class.java)
        loadInfoData()
        setNavigationViewVisibility()
        getData(requireArguments())
        setRecyclerViewSettings()
        loadData()
        setAdapterListener()
        setButtonsListeners()
    }

    private fun loadInfoData() {
        viewModelInfo.userInfoLiveData.observe(viewLifecycleOwner, {
            currentUserInfo = it
        })
        viewModelInfo.requestUserInfo(1)
    }

    private fun getData(bundle: Bundle) {
        workTime = bundle.getLong("workTime")
        val workoutName = bundle.getString("workout_name")
        val workoutImageId = bundle.getInt("workout_image_id")
        difficulty = bundle.getInt("difficulty")
        totalTime = bundle.getInt("totalTime")
        difficultyType = bundle.getString("difficulty_type") as String
        binding?.run {
            tvWorkoutName.text = workoutName
            ivWorkout.setImageResource(workoutImageId)
        }
    }

    private fun setRecyclerViewSettings() {
        binding?.run {
            adapter = ExercisesDataAdapter(workTime)
            rvExercises.adapter = adapter
            rvExercises.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun loadData() {
        viewModel.listOfExercisesLiveData.observe(viewLifecycleOwner, { list ->
            adapter.updateList(list)
        })
        viewModel.requestExercisesByDifficulty(difficultyType)
    }

    private fun setNavigationViewVisibility() {
        currentActivity.setNavigationViewVisibility(true)
    }

    private fun setButtonsListeners() {
        binding?.run {
            buttonBack.setOnClickListener {
                when (currentUserInfo.sex) {
                    "мужской" -> currentActivity.loadFragment(FragmentWorkoutsMale())
                    "женский" -> currentActivity.loadFragment(FragmentWorkoutsFemale())
                }
            }
            buttonStartWorkout.setOnClickListener {
                if (difficultyType != "hit_demon") {
                    currentActivity.loadFragment(
                        FragmentCurrentExercise::class.java,
                        bundleOf(
                            "list" to adapter.getList(),
                            "workTime" to workTime,
                            "difficulty" to difficulty,
                            "totalTime" to totalTime
                        )
                    )
                } else {
                    currentActivity.loadFragment(
                        FragmentCurrentExerciseHit::class.java,
                        bundleOf(
                            "list" to adapter.getList(),
                            "workTime" to workTime,
                            "difficulty" to difficulty,
                            "totalTime" to totalTime
                        )
                    )
                }
            }
        }
    }

    private fun setAdapterListener() {
        adapter.onItemClickListener = {
            currentActivity.loadDialogFragment(DialogFragmentExerciseInfo(it))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}