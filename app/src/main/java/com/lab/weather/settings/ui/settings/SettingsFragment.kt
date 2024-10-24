package com.lab.weather.settings.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.lab.weather.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }
}