package com.yowayowa.yawning

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceScreen

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        val versionPreferenceScreen = findPreference<PreferenceScreen>("version")
        versionPreferenceScreen?.summary = BuildConfig.VERSION_NAME
    }
}
