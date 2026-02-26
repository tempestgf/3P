package com.tempestgf.threep.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tempestgf.threep.domain.Trip
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripScreen(
    onNavigateBack: () -> Unit,
    onNavigateToItinerary: (String) -> Unit
) {
    // Mocked Data
    val mockedTrips = listOf(
        Trip("1", "Summer in Paris", "Paris, France", Date(), Date()),
        Trip("2", "Tokyo Adventure", "Tokyo, Japan", Date(), Date()),
        Trip("3", "New York Business", "New York, USA", Date(), Date())
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Trips") },
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
            items(mockedTrips) { trip ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { onNavigateToItinerary(trip.id) },
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = trip.title, style = MaterialTheme.typography.titleLarge)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = trip.destination, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}
