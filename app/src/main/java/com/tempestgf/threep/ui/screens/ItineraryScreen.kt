package com.tempestgf.threep.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tempestgf.threep.domain.Activity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItineraryScreen(
    tripId: String,
    onNavigateBack: () -> Unit
) {
    // Mocked Data
    val mockedActivities = listOf(
        Activity("1", "Visit Louvre", "Art museum tour", "10:00 AM", "13:00 PM", "Louvre Museum", 20.0),
        Activity("2", "Lunch at Cafe", "Local french food", "13:30 PM", "15:00 PM", "Le Cafe", 35.0),
        Activity("3", "Eiffel Tower", "Sightseeing", "16:00 PM", "18:00 PM", "Eiffel Tower", 25.0)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Itinerary (Trip $tripId)") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            items(mockedActivities) { activity ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = activity.title, style = MaterialTheme.typography.titleMedium)
                        Text(text = "${activity.startTime} - ${activity.endTime}", style = MaterialTheme.typography.bodySmall)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = activity.location, style = MaterialTheme.typography.bodyMedium)
                        Text(text = "Cost: $${activity.cost}", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}
