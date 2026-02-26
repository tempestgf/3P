package com.tempestgf.threep.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tempestgf.threep.ui.screens.HomeScreen
import com.tempestgf.threep.ui.screens.ItineraryScreen
import com.tempestgf.threep.ui.screens.PreferencesScreen
import com.tempestgf.threep.ui.screens.TripScreen
import com.tempestgf.threep.ui.screens.AboutScreen
import com.tempestgf.threep.ui.screens.TermsScreen
import com.tempestgf.threep.ui.screens.SplashScreen
import com.tempestgf.threep.ui.screens.TripDetailScreen
import com.tempestgf.threep.ui.screens.TripGalleryScreen

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object Trip : Screen("trip")
    object TripDetail : Screen("trip_detail/{tripId}") {
        fun createRoute(tripId: String) = "trip_detail/$tripId"
    }
    object TripGallery : Screen("trip_gallery/{tripId}") {
        fun createRoute(tripId: String) = "trip_gallery/$tripId"
    }
    object Itinerary : Screen("itinerary/{tripId}") {
        fun createRoute(tripId: String) = "itinerary/$tripId"
    }
    object Preferences : Screen("preferences")
    object About : Screen("about")
    object Terms : Screen("terms")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToTripDetail = { tripId -> navController.navigate(Screen.TripDetail.createRoute(tripId)) },
                onNavigateToPreferences = { navController.navigate(Screen.Preferences.route) },
                onNavigateToAbout = { navController.navigate(Screen.About.route) },
                onNavigateToTerms = { navController.navigate(Screen.Terms.route) }
            )
        }
        
        composable(Screen.Trip.route) {
            TripScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToItinerary = { tripId ->
                    navController.navigate(Screen.Itinerary.createRoute(tripId))
                }
            )
        }

        composable(
            route = Screen.TripDetail.route,
            arguments = listOf(navArgument("tripId") { type = NavType.StringType })
        ) { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId") ?: ""
            TripDetailScreen(
                tripId = tripId,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToGallery = { id -> navController.navigate(Screen.TripGallery.createRoute(id)) }
            )
        }

        composable(
            route = Screen.TripGallery.route,
            arguments = listOf(navArgument("tripId") { type = NavType.StringType })
        ) { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId") ?: ""
            TripGalleryScreen(
                tripId = tripId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(
            route = Screen.Itinerary.route,
            arguments = listOf(navArgument("tripId") { type = NavType.StringType })
        ) { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId") ?: ""
            ItineraryScreen(
                tripId = tripId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.Preferences.route) {
            PreferencesScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.About.route) {
            AboutScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.Terms.route) {
            TermsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
