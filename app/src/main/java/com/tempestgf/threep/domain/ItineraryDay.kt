package com.tempestgf.threep.domain

import java.util.Date

data class ItineraryDay(
    val id: String,
    val tripId: String,
    val date: Date,
    val activities: List<Activity>
) {
    // @TODO: Implement logic to sort activities by time
    fun sortActivities() {
        // Pending implementation
    }
}
