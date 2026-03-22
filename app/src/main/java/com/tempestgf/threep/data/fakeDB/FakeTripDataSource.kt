package com.tempestgf.threep.data.fakeDB

import com.tempestgf.threep.domain.Trip
import com.tempestgf.threep.domain.Activity

object FakeTripDataSource {
    private val trips = mutableListOf<Trip>()

    fun getTrips(): List<Trip> = trips.toList()

    fun getTripById(tripId: String): Trip? = trips.find { it.id == tripId }

    fun addTrip(trip: Trip) {
        trips.add(trip)
    }

    fun editTrip(trip: Trip) {
        val index = trips.indexOfFirst { it.id == trip.id }
        if (index != -1) {
            trips[index] = trip
        }
    }

    fun deleteTrip(tripId: String) {
        trips.removeIf { it.id == tripId }
    }

    fun addActivity(tripId: String, activity: Activity) {
        val trip = getTripById(tripId)
        if (trip != null) {
            val updatedActivities = trip.activities.toMutableList().apply { add(activity) }
            editTrip(trip.copy(activities = updatedActivities))
        }
    }

    fun updateActivity(tripId: String, activity: Activity) {
        val trip = getTripById(tripId)
        if (trip != null) {
            val updatedActivities = trip.activities.map { 
                if (it.id == activity.id) activity else it 
            }
            editTrip(trip.copy(activities = updatedActivities))
        }
    }

    fun deleteActivity(tripId: String, activityId: String) {
        val trip = getTripById(tripId)
        if (trip != null) {
            val updatedActivities = trip.activities.filter { it.id != activityId }
            editTrip(trip.copy(activities = updatedActivities))
        }
    }
}
