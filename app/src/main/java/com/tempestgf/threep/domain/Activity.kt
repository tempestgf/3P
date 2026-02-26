package com.tempestgf.threep.domain

data class Activity(
    val id: String,
    val title: String,
    val description: String,
    val startTime: String,
    val endTime: String,
    val location: String,
    val cost: Double
) {
    // @TODO: Implement logic to validate time range
    fun isValidTimeRange(): Boolean {
        // Pending implementation
        return true
    }
}
