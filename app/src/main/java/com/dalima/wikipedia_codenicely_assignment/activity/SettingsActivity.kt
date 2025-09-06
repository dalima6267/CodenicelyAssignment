package com.dalima.wikipedia_codenicely_assignment.activity

import android.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dalima.wikipedia_codenicely_assignment.fragment.SettingsFragment

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content, SettingsFragment())
            .commit()
    }
}