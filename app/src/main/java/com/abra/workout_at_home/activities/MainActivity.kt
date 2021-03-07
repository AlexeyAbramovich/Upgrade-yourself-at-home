package com.abra.workout_at_home.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.*
import androidx.lifecycle.ViewModelProvider
import com.abra.workout_at_home.R
import com.abra.workout_at_home.controller.NavigationViewController
import com.abra.workout_at_home.data.UserInfo
import com.abra.workout_at_home.fragments.*
import com.abra.workout_at_home.viewmodels.UserInfoViewModel
import com.abra.workout_at_home.viewmodels.UserInfoViewModelFactory
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job


class MainActivity : AppCompatActivity(), NavigationViewController, FragmentLoader,
    NavigationView.OnNavigationItemSelectedListener {
    private lateinit var viewModel: UserInfoViewModel
    private lateinit var drawer: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var currentUserInfo: UserInfo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawer = findViewById(R.id.drawer_layout)
        toolbar = findViewById(R.id.toolbar)
        navigationView = findViewById(R.id.nav_view)
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        viewModel = ViewModelProvider(
            this,
            UserInfoViewModelFactory(CoroutineScope(Dispatchers.Main + Job()))
        )
            .get(UserInfoViewModel::class.java)
        loadData()
        loadFragment()
        navigationView.setNavigationItemSelectedListener(this)
        navigationView.setCheckedItem(R.id.statistic)
    }

    override fun loadData() {
        viewModel.userInfoLiveData.observe(this, {
            currentUserInfo = it
        })
        viewModel.requestUserInfo(1)
    }

    private fun loadFragment() {
        supportFragmentManager.commit {
            add<FragmentUserStatistic>(R.id.fragmentContainer)
        }
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START)
    }

    override fun setNavigationViewVisibility(hide: Boolean) {
        if (hide) {
            supportActionBar?.hide()
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        } else {
            supportActionBar?.show()
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        }
    }

    override fun loadFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.fragmentContainer, fragment)
        }
    }

    override fun loadFragment(fragmentClass: Class<out Fragment>, bundle: Bundle) {
        supportFragmentManager.commit {
            replace(R.id.fragmentContainer, fragmentClass, bundle)
        }
    }

    override fun loadDialogFragment(fragment: DialogFragment) {
        fragment.show(supportFragmentManager, "dialog")
    }

    override fun loadFragmentUserStatistic() {
        supportFragmentManager.commit {
            replace<FragmentUserStatistic>(R.id.fragmentContainer)
        }
        navigationView.setCheckedItem(R.id.statistic)
        toolbar.title = resources.getString(R.string.statistic)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.listOfWorkouts -> {
                toolbar.title = resources.getString(R.string.list_of_workouts)
                when (currentUserInfo.sex) {
                    "мужской" -> loadFragment(FragmentWorkoutsMale())
                    "женский" -> loadFragment(FragmentWorkoutsFemale())
                }

            }
            R.id.statistic -> {
                toolbar.title = resources.getString(R.string.statistic)
                loadFragment(FragmentUserStatistic())
            }
            R.id.settings -> {
                toolbar.title = resources.getString(R.string.settings)
                loadFragment(FragmentSettings())
            }
            R.id.author -> {
                toolbar.title = resources.getString(R.string.author)
                loadFragment(FragmentConnectionWithAuthor())
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}