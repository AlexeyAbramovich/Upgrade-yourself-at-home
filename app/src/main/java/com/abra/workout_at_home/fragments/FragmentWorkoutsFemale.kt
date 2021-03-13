package com.abra.workout_at_home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.abra.workout_at_home.R
import com.abra.workout_at_home.activities.MainActivity
import com.abra.workout_at_home.databinding.FragmentWorkoutsFemaleBinding

class FragmentWorkoutsFemale : Fragment() {
    private lateinit var currentActivity: MainActivity
    private var binding: FragmentWorkoutsFemaleBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkoutsFemaleBinding.bind(
            inflater.inflate(
                R.layout.fragment_workouts_female,
                container,
                false
            )
        )
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentActivity = requireActivity() as MainActivity
        setNavigationViewVisibility()
        setWorkoutsListeners()
    }

    private fun setWorkoutsListeners() {
        binding?.run {
            cvCoreEasy.setOnClickListener {
                currentActivity.loadFragment(
                    FragmentWorkout::class.java,
                    bundleOf(
                        "workTime" to 30L,
                        "workout_name" to getString(R.string.core_easy),
                        "workout_image_id" to R.drawable.core_easy_female,
                        "difficulty" to 2,
                        "totalTime" to 5,
                        "difficulty_type" to "core_easy"
                    )
                )
            }
            cvCoreNormal.setOnClickListener {
                currentActivity.loadFragment(
                    FragmentWorkout::class.java,
                    bundleOf(
                        "workTime" to 30L,
                        "workout_name" to getString(R.string.core_normal),
                        "workout_image_id" to R.drawable.core_normal_female,
                        "difficulty" to 4,
                        "totalTime" to 7,
                        "difficulty_type" to "core_normal"
                    )
                )
            }
            cvCoreAdvanced.setOnClickListener {
                currentActivity.loadFragment(
                    FragmentWorkout::class.java,
                    bundleOf(
                        "workTime" to 30L,
                        "workout_name" to getString(R.string.core_advanced),
                        "workout_image_id" to R.drawable.core_advanced_female,
                        "difficulty" to 5,
                        "totalTime" to 8,
                        "difficulty_type" to "core_advanced"
                    )
                )
            }
            cvHitDemon.setOnClickListener {
                currentActivity.loadFragment(
                    FragmentWorkout::class.java,
                    bundleOf(
                        "workTime" to 20L,
                        "workout_name" to getString(R.string.hit_demon),
                        "workout_image_id" to R.drawable.hit_demon_female,
                        "difficulty" to 6,
                        "totalTime" to 11,
                        "difficulty_type" to "hit_demon"
                    )
                )
            }
        }
    }

    private fun setNavigationViewVisibility() {
        currentActivity.setNavigationViewVisibility(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}