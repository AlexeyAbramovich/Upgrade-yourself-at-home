package com.abra.workout_at_home.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.abra.workout_at_home.R
import com.abra.workout_at_home.data.ExercisesData
import com.abra.workout_at_home.databinding.FragmentExerciseInfoBinding
import com.bumptech.glide.Glide

class DialogFragmentExerciseInfo(
    private val info: ExercisesData
) : DialogFragment() {
    private var binding: FragmentExerciseInfoBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExerciseInfoBinding.bind(
            inflater.inflate(
                R.layout.fragment_exercise_info,
                container,
                false
            )
        )
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        setButtonListener()
    }

    private fun setData() {
        binding?.run {
            tvExerciseName.text = info.name
            tvDescription.text = info.description
            Glide.with(context as Context).load(Uri.parse(info.path)).into(ivExercise)
        }
    }

    private fun setButtonListener() {
        binding?.buttonClose?.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}