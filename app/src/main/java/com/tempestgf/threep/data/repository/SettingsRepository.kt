package com.tempestgf.threep.data.repository

import android.content.Context
import android.content.SharedPreferences

class SettingsRepository(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_settings", Context.MODE_PRIVATE)

    var username: String
        get() = prefs.getString("username", "") ?: ""
        set(value) = prefs.edit().putString("username", value).apply()

    var dateOfBirth: String
        get() = prefs.getString("dob", "") ?: ""
        set(value) = prefs.edit().putString("dob", value).apply()

    var darkMode: Boolean
        get() = prefs.getBoolean("darkMode", false)
        set(value) = prefs.edit().putBoolean("darkMode", value).apply()

    var language: String
        get() = prefs.getString("language", "en") ?: "en"
        set(value) = prefs.edit().putString("language", value).apply()
}
