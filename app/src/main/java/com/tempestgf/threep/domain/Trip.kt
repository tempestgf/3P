package com.tempestgf.threep.domain

import java.util.Date

data class Trip(
    val id: String,
    val title: String,
    val destination: String,
    val startDate: Date,
    val endDate: Date,
    val isArchived: Boolean = false
) {
    // @TODO: Implement logic to calculate total trip duration
    fun getDurationInDays(): Int {
        // Pending implementation
        return 0
    }
}
