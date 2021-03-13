package com.abra.workout_at_home.fragments

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

interface FragmentLoader {
    fun loadData()
    fun loadFragment(fragment: Fragment)
    fun loadFragment(fragmentClass: Class<out Fragment>, bundle: Bundle)
    fun loadDialogFragment(fragment: DialogFragment)
    fun loadFragmentUserStatistic()
}