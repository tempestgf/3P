package com.tempestgf.threep.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tempestgf.threep.data.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel(private val repository: SettingsRepository) : ViewModel() {
    private val _username = MutableStateFlow(repository.username)
    val username: StateFlow<String> = _username.asStateFlow()

    private val _dateOfBirth = MutableStateFlow(repository.dateOfBirth)
    val dateOfBirth: StateFlow<String> = _dateOfBirth.asStateFlow()

    private val _darkMode = MutableStateFlow(repository.darkMode)
    val darkMode: StateFlow<Boolean> = _darkMode.asStateFlow()

    private val _language = MutableStateFlow(repository.language)
    val language: StateFlow<String> = _language.asStateFlow()

    fun updateUsername(name: String) {
        repository.username = name
        _username.value = name
    }

    fun updateDateOfBirth(dob: String) {
        repository.dateOfBirth = dob
        _dateOfBirth.value = dob
    }

    fun toggleDarkMode(enabled: Boolean) {
        repository.darkMode = enabled
        _darkMode.value = enabled
    }

    fun updateLanguage(lang: String) {
        repository.language = lang
        _language.value = lang
    }
}

class SettingsViewModelFactory(private val repository: SettingsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
