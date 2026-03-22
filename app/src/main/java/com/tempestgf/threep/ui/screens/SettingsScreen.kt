package com.tempestgf.threep.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tempestgf.threep.R
import com.tempestgf.threep.ui.viewmodels.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(viewModel: SettingsViewModel) {
    val username by viewModel.username.collectAsState()
    val dateOfBirth by viewModel.dateOfBirth.collectAsState()
    val darkMode by viewModel.darkMode.collectAsState()
    val language by viewModel.language.collectAsState()

    var expandedLanguage by remember { mutableStateOf(false) }
    val languages = mapOf("en" to "English", "es" to "Español", "ca" to "Català")

    Scaffold(
        topBar = { TopAppBar(title = { Text(stringResource(R.string.title_settings)) }) }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp).fillMaxSize()) {
            OutlinedTextField(
                value = username,
                onValueChange = { viewModel.updateUsername(it) },
                label = { Text(stringResource(R.string.username)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = dateOfBirth,
                onValueChange = { viewModel.updateDateOfBirth(it) },
                label = { Text(stringResource(R.string.dob)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = stringResource(R.string.dark_mode))
                Switch(checked = darkMode, onCheckedChange = { viewModel.toggleDarkMode(it) })
            }
            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = expandedLanguage,
                onExpandedChange = { expandedLanguage = !expandedLanguage }
            ) {
                OutlinedTextField(
                    readOnly = true,
                    value = languages[language] ?: "English",
                    onValueChange = { },
                    label = { Text(stringResource(R.string.language)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedLanguage) },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(expanded = expandedLanguage, onDismissRequest = { expandedLanguage = false }) {
                    languages.forEach { (code, name) ->
                        DropdownMenuItem(
                            text = { Text(text = name) },
                            onClick = {
                                viewModel.updateLanguage(code)
                                expandedLanguage = false
                            }
                        )
                    }
                }
            }
        }
    }
}
