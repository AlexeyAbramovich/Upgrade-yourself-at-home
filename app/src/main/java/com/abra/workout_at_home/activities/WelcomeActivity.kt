package com.abra.workout_at_home.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.abra.workout_at_home.R

class WelcomeActivity : AppCompatActivity() {
    private lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_screen)
        button = findViewById(R.id.button_start)
        setButtonListener()
    }

    private fun setButtonListener() {
        button.setOnClickListener {
            startActivity(Intent(this, UserInfoActivity::class.java))
        }
    }

    override fun onBackPressed() {}
}