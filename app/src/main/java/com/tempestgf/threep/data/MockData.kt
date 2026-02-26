package com.tempestgf.threep.data

import com.tempestgf.threep.domain.Activity
import com.tempestgf.threep.domain.Trip
import java.util.Date

object MockData {
    val trips = listOf(
        Trip("1", "Summer in Paris", "Paris, France", Date(), Date()),
        Trip("2", "Tokyo Adventure", "Tokyo, Japan", Date(), Date()),
        Trip("3", "New York Business", "New York, USA", Date(), Date()),
        Trip("4", "Rome Getaway", "Rome, Italy", Date(), Date())
    )

    val activities = listOf(
        Activity("1", "Visit Louvre", "Art museum tour", "10:00 AM", "13:00 PM", "Louvre Museum", 20.0),
        Activity("2", "Lunch at Cafe", "Local french food", "13:30 PM", "15:00 PM", "Le Cafe", 35.0),
        Activity("3", "Eiffel Tower", "Sightseeing", "16:00 PM", "18:00 PM", "Eiffel Tower", 25.0),
        Activity("4", "Seine River Cruise", "Evening boat ride", "19:00 PM", "21:00 PM", "Seine River", 40.0)
    )
}
