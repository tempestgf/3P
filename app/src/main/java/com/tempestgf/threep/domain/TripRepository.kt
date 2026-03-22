package com.tempestgf.threep.domain

interface TripRepository {
    fun getTrips(): List<Trip>
    fun getTripById(tripId: String): Trip?
    fun addTrip(trip: Trip)
    fun editTrip(trip: Trip)
    fun deleteTrip(tripId: String)
    
    fun addActivity(tripId: String, activity: Activity)
    fun updateActivity(tripId: String, activity: Activity)
    fun deleteActivity(tripId: String, activityId: String)
}
