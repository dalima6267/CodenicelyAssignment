package com.dalima.wikipedia_codenicely_assignment.fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.dalima.wikipedia_codenicely_assignment.R

class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

        setPreferencesFromResource(R.xml.preferences, rootKey)

        val themePref = findPreference<ListPreference>("theme_pref")
        themePref?.onPreferenceChangeListener = this


        val current = themePref?.value ?: "system"
        themePref?.summary = when (current) {
            "light" -> "Light"
            "dark" -> "Dark"
            else -> "System Default"
        }
    }

    override fun onPreferenceChange(preference: Preference, newValue: Any?): Boolean {
        val value = newValue as? String
        when (value) {
            "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            "system" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }


        if (preference is ListPreference) {
            preference.summary = when (value) {
                "light" -> "Light"
                "dark" -> "Dark"
                else -> "System Default"
            }
        }
        return true
    }
}