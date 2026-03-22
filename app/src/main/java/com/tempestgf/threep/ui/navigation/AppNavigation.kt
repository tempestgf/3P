package com.tempestgf.threep.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.tempestgf.threep.R
import com.tempestgf.threep.ui.screens.AddTripScreen
import com.tempestgf.threep.ui.screens.EditTripScreen
import com.tempestgf.threep.ui.screens.SettingsScreen
import com.tempestgf.threep.ui.screens.TripDetailScreen
import com.tempestgf.threep.ui.screens.TripListScreen
import com.tempestgf.threep.ui.screens.AboutScreen
import com.tempestgf.threep.ui.screens.TermsScreen
import com.tempestgf.threep.ui.screens.TripGalleryScreen
import com.tempestgf.threep.ui.viewmodels.SettingsViewModel
import com.tempestgf.threep.ui.viewmodels.TripListViewModel

@Composable
fun MainAppNavigation(
    navController: NavHostController = rememberNavController(),
    tripViewModel: TripListViewModel,
    settingsViewModel: SettingsViewModel
) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "tripList",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("tripList") {
                TripListScreen(
                    viewModel = tripViewModel,
                    onNavigateToAddTrip = { navController.navigate("addTrip") },
                    onNavigateToTripDetail = { tripId -> navController.navigate("tripDetail/${tripId}") },
                    onNavigateToAbout = { navController.navigate("about") },
                    onNavigateToTerms = { navController.navigate("terms") }
                )
            }
            composable("about") {
                AboutScreen(onNavigateBack = { navController.popBackStack() })
            }
            composable("terms") {
                TermsScreen(onNavigateBack = { navController.popBackStack() })
            }
            composable("addTrip") {
                AddTripScreen(
                    viewModel = tripViewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable(
                route = "editTrip/{tripId}",
                arguments = listOf(navArgument("tripId") { type = NavType.StringType })
            ) { backStackEntry ->
                val tripId = backStackEntry.arguments?.getString("tripId") ?: return@composable
                EditTripScreen(
                    tripId = tripId,
                    viewModel = tripViewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable(
                route = "tripDetail/{tripId}",
                arguments = listOf(navArgument("tripId") { type = NavType.StringType })
            ) { backStackEntry ->
                val tripId = backStackEntry.arguments?.getString("tripId") ?: return@composable
                TripDetailScreen(
                    tripId = tripId,
                    viewModel = tripViewModel,
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToEditTrip = { id -> navController.navigate("editTrip/${id}") },
                    onNavigateToGallery = { id -> navController.navigate("tripGallery/${id}") }
                )
            }
            composable(
                route = "tripGallery/{tripId}",
                arguments = listOf(navArgument("tripId") { type = NavType.StringType })
            ) { backStackEntry ->
                val tripId = backStackEntry.arguments?.getString("tripId") ?: return@composable
                TripGalleryScreen(
                    tripId = tripId,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable("settings") {
                SettingsScreen(viewModel = settingsViewModel)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text(stringResource(R.string.trips)) },
            selected = currentRoute?.startsWith("trip") == true,
            onClick = {
                navController.navigate("tripList") {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
            label = { Text(stringResource(R.string.title_settings)) },
            selected = currentRoute == "settings",
            onClick = {
                navController.navigate("settings") {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }
        )
    }
}
