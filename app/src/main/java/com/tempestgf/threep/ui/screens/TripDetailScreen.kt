package com.tempestgf.threep.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tempestgf.threep.data.MockData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripDetailScreen(
    tripId: String,
    onNavigateBack: () -> Unit,
    onNavigateToGallery: (String) -> Unit
) {
    val trip = MockData.trips.find { it.id == tripId } ?: MockData.trips.first()
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Itinerary", "Gallery")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(trip.title) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Trip Stats
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Destination", style = MaterialTheme.typography.labelMedium)
                        Text(trip.destination, style = MaterialTheme.typography.bodyLarge)
                    }
                    Column {
                        Text("Total Budget", style = MaterialTheme.typography.labelMedium)
                        Text("$1,200", style = MaterialTheme.typography.bodyLarge)
                    }
                    Column {
                        Text("Duration", style = MaterialTheme.typography.labelMedium)
                        Text("5 Days", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }

            // Tabs
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = {
                            selectedTabIndex = index
                            if (index == 1) {
                                onNavigateToGallery(tripId)
                                selectedTabIndex = 0 // Reset so it doesn't stay stuck if we come back
                            }
                        },
                        text = { Text(title) }
                    )
                }
            }

            // Itinerary List
            if (selectedTabIndex == 0) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(MockData.activities) { activity ->
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
    }
}
