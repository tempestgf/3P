package com.tempestgf.threep.ui.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tempestgf.threep.R
import com.tempestgf.threep.ui.viewmodels.TripListViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripDetailScreen(
    tripId: String,
    viewModel: TripListViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToEditTrip: (String) -> Unit,
    onNavigateToGallery: (String) -> Unit
) {
    val trips by viewModel.trips.collectAsState()
    val trip = trips.find { it.id == tripId }
    val errorMessage by viewModel.errorMessage.collectAsState()
    var showAddActivityDialog by remember { mutableStateOf(false) }

    if (trip == null) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(stringResource(R.string.trip_not_found), modifier = Modifier.padding(16.dp))
            Button(onClick = onNavigateBack, modifier = Modifier.padding(16.dp)) { Text(stringResource(R.string.back_button)) }
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(trip.title) },
                actions = {
                    IconButton(onClick = { onNavigateToGallery(trip.id) }) {
                        Icon(Icons.Default.Photo, contentDescription = stringResource(R.string.gallery_title))
                    }
                    IconButton(onClick = { onNavigateToEditTrip(trip.id) }) {
                        Icon(Icons.Default.Edit, contentDescription = stringResource(R.string.edit_trip))
                    }
                    IconButton(onClick = {
                        viewModel.deleteTrip(tripId)
                        onNavigateBack()
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = stringResource(R.string.delete_trip))
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddActivityDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add_activity))
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp).fillMaxSize()) {
            Text(text = "${trip.startDate} to ${trip.endDate}", style = MaterialTheme.typography.titleMedium)
            Text(text = trip.description, modifier = Modifier.padding(top = 8.dp, bottom = 16.dp))

            Text(text = stringResource(R.string.activities), style = MaterialTheme.typography.headlineSmall)
            
            LazyColumn {
                items(trip.activities) { activity ->
                    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column {
                                Text(text = activity.title, style = MaterialTheme.typography.titleMedium)
                                Text(text = "${activity.date} at ${activity.time}")
                                Text(text = activity.description, style = MaterialTheme.typography.bodySmall)
                            }
                            IconButton(onClick = { viewModel.deleteActivity(tripId, activity.id) }) {
                                Icon(Icons.Default.Delete, contentDescription = stringResource(R.string.delete))
                            }
                        }
                    }
                }
            }
        }

        if (showAddActivityDialog) {
            AddActivityDialog(
                viewModel = viewModel,
                tripId = tripId,
                onDismiss = { showAddActivityDialog = false }
            )
        }
    }
}

@Composable
fun AddActivityDialog(
    viewModel: TripListViewModel,
    tripId: String,
    onDismiss: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var selectedTime by remember { mutableStateOf<LocalTime?>(null) }
    
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            selectedTime = LocalTime.of(hourOfDay, minute)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )

    val errorMessage by viewModel.errorMessage.collectAsState()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.add_activity)) },
        text = {
            Column {
                errorMessage?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(bottom = 8.dp))
                }
                    OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text(stringResource(R.string.title)) }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text(stringResource(R.string.description)) })
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { datePickerDialog.show() }, modifier = Modifier.fillMaxWidth()) {
                    Text(selectedDate?.toString() ?: stringResource(R.string.select_date))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { timePickerDialog.show() }, modifier = Modifier.fillMaxWidth()) {
                    Text(selectedTime?.toString() ?: stringResource(R.string.select_time))
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                if (selectedDate != null && selectedTime != null) {
                    val success = viewModel.addActivity(tripId, title, description, selectedDate!!, selectedTime!!)
                    if (success) {
                        viewModel.clearError()
                        onDismiss()
                    }
                } else {
                    viewModel.addActivity(tripId, title, description, LocalDate.now(), LocalTime.now()) // Will trigger error in VM
                }
            }) { Text(stringResource(R.string.save)) }
        },
        dismissButton = {
            Button(onClick = {
                viewModel.clearError()
                onDismiss()
            }) { Text(stringResource(R.string.cancel)) }
        }
    )
}
