package com.tempestgf.threep

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.tempestgf.threep.data.repository.SettingsRepository
import com.tempestgf.threep.ui.navigation.MainAppNavigation
import com.tempestgf.threep.ui.theme.TravelPlannerTheme
import com.tempestgf.threep.ui.viewmodels.SettingsViewModel
import com.tempestgf.threep.ui.viewmodels.SettingsViewModelFactory
import com.tempestgf.threep.ui.viewmodels.TripListViewModel
import java.util.Locale
import android.content.res.Configuration

class MainActivity : ComponentActivity() {

    private val tripViewModel: TripListViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels {
        SettingsViewModelFactory(SettingsRepository(applicationContext))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val darkMode by settingsViewModel.darkMode.collectAsState()
            val language by settingsViewModel.language.collectAsState()

            updateLocale(language)

            TravelPlannerTheme(
                darkTheme = darkMode
            ) {
                MainAppNavigation(
                    tripViewModel = tripViewModel,
                    settingsViewModel = settingsViewModel
                )
            }
        }
    }

    private fun updateLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        @Suppress("DEPRECATION")
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}
