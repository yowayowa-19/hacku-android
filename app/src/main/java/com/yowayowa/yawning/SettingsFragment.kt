package com.yowayowa.yawning

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.PreferenceScreen

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        val versionPreferenceScreen = findPreference<PreferenceScreen>("version")
        versionPreferenceScreen?.summary = BuildConfig.VERSION_NAME

        val logoutPreferenceScreen = findPreference<PreferenceScreen>("logout")
        logoutPreferenceScreen?.setOnPreferenceClickListener {
            startActivity(Intent(context,FirstViewActivity::class.java))
            return@setOnPreferenceClickListener true
        }
        val pref : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        findPreference<PreferenceScreen>("userName")?.summary =
            pref.getString("userName","Unknown")
        findPreference<PreferenceScreen>("userID")?.summary =
            pref.getInt("userID",0).toString()
    }
}
