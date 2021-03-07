package com.abra.workout_at_home.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.abra.workout_at_home.R
import com.abra.workout_at_home.data.UserInfo
import com.abra.workout_at_home.databinding.FragmentStatisticScreenBinding
import com.abra.workout_at_home.usefulclasses.UserAchievements
import com.abra.workout_at_home.viewmodels.UserInfoViewModel
import com.abra.workout_at_home.viewmodels.UserInfoViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class FragmentUserStatistic : Fragment() {
    private lateinit var viewModel: UserInfoViewModel
    private var binding: FragmentStatisticScreenBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStatisticScreenBinding.bind(
            inflater.inflate(R.layout.fragment_statistic_screen, container, false)
        )
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            this, UserInfoViewModelFactory(CoroutineScope(Dispatchers.Main + Job()))
        ).get(UserInfoViewModel::class.java)
        viewModel.userInfoLiveData.observe(viewLifecycleOwner, { data ->
            setDataIntoViews(data)
        })
        viewModel.requestUserInfo(1)
    }

    private fun setDataIntoViews(data: UserInfo) {
        binding?.run {
            tvUserName.text = data.name
            textCurrentLevel.text = data.level.toString()
            textViewNumberOfWorkouts.text = data.workouts.toString()
            textViewNumberOfKals.text = data.kkals.toString()
            textViewRang.text = data.rang
            progressBarLevel.progress = data.exp
        }
        setAchievementsState(data.workouts)
    }

    private fun setAchievementsState(workouts: Int) {
        var arrayOfAchievements = arrayOf<ImageView>()
        var arrayOfTextAchievements = arrayOf<TextView>()
        binding?.run {
            arrayOfAchievements = arrayOf(iv5w, iv10w, iv30w, iv60w, iv100w, iv150w)
            arrayOfTextAchievements = arrayOf(tv5w, tv10w, tv30w, tv60w, tv100w, tv150w)
        }
        val userAchievements = UserAchievements(
            workouts,
            arrayOfAchievements,
            arrayOfTextAchievements,
            context as Context
        )
        userAchievements.setAchievementsState()
        binding?.textViewNumberOfAchievements?.text =
            userAchievements.getAchievementsNumber().toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}