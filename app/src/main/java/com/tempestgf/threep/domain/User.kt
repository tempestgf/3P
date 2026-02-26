package com.tempestgf.threep.domain

data class UserPreferences(
    val darkMode: Boolean,
    val notificationsEnabled: Boolean,
    val currency: String
)

data class User(
    val id: String,
    val name: String,
    val email: String,
    val preferences: UserPreferences
) {
    // @TODO: Implement user authentication logic
    fun authenticate() {
        // Pending implementation
    }
}
