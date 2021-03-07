package com.abra.workout_at_home.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.abra.workout_at_home.R
import com.abra.workout_at_home.databinding.FragmentConnectionWithAuthorBinding
import com.google.android.material.snackbar.Snackbar

class FragmentConnectionWithAuthor : Fragment() {
    private var binding: FragmentConnectionWithAuthorBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConnectionWithAuthorBinding.bind(
            inflater.inflate(
                R.layout.fragment_connection_with_author,
                container,
                false
            )
        )
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtonListeners()
    }

    private fun setButtonListeners() {
        binding?.run {
            ibSend.setOnClickListener {
                sendMail()
            }
            btVk.setOnClickListener {
                goToUrl()
            }
        }
    }

    private fun goToUrl() {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/just_abra")))
    }

    private fun sendMail() {
        binding?.run {
            val theme = etTheme.text.toString()
            val message = etMessage.text.toString()
            val array = arrayOf(authorEmail.text.toString())
            if (theme.isNotEmpty() && message.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_SEND)
                intent.putExtra(Intent.EXTRA_EMAIL, array)
                intent.putExtra(Intent.EXTRA_SUBJECT, theme)
                intent.putExtra(Intent.EXTRA_TEXT, message)
                intent.type = "message/rfc822"
                startActivity(Intent.createChooser(intent, "Выберите почтовый клиент"))
            } else {
                Snackbar.make(root, "Заполните все поля!", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}