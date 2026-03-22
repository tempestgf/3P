package com.tempestgf.threep.ui.screens

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tempestgf.threep.R
import com.tempestgf.threep.ui.viewmodels.TripListViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTripScreen(
    tripId: String,
    viewModel: TripListViewModel,
    onNavigateBack: () -> Unit
) {
    val trip = viewModel.getTrip(tripId)
    if (trip == null) {
        Column {
            Text("Trip not found")
            Button(onClick = onNavigateBack) { Text("Back") }
        }
        return
    }

    var title by remember { mutableStateOf(trip.title) }
    var description by remember { mutableStateOf(trip.description) }
    var startDate by remember { mutableStateOf(trip.startDate) }
    var endDate by remember { mutableStateOf(trip.endDate) }

    val errorMessage by viewModel.errorMessage.collectAsState()
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val startDatePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            startDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val endDatePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            endDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Scaffold(
        topBar = { TopAppBar(title = { Text("Edit Trip") }) }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).padding(16.dp).fillMaxSize()
        ) {
            errorMessage?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(bottom = 8.dp))
            }

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text(stringResource(R.string.title)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text(stringResource(R.string.description)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = { startDatePickerDialog.show() }, modifier = Modifier.fillMaxWidth()) {
                Text("Start: $startDate")
            }
            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = { endDatePickerDialog.show() }, modifier = Modifier.fillMaxWidth()) {
                Text("End: $endDate")
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val success = viewModel.editTrip(tripId, title, startDate, endDate, description)
                    if (success) {
                        viewModel.clearError()
                        onNavigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.save))
            }
        }
    }
}
