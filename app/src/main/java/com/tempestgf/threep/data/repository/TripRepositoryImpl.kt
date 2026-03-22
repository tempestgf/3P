package com.tempestgf.threep.data.repository

import com.tempestgf.threep.data.fakeDB.FakeTripDataSource
import com.tempestgf.threep.domain.Activity
import com.tempestgf.threep.domain.Trip
import com.tempestgf.threep.domain.TripRepository

class TripRepositoryImpl : TripRepository {
    override fun getTrips(): List<Trip> = FakeTripDataSource.getTrips()
    
    override fun getTripById(tripId: String): Trip? = FakeTripDataSource.getTripById(tripId)
    
    override fun addTrip(trip: Trip) = FakeTripDataSource.addTrip(trip)
    
    override fun editTrip(trip: Trip) = FakeTripDataSource.editTrip(trip)
    
    override fun deleteTrip(tripId: String) = FakeTripDataSource.deleteTrip(tripId)
    
    override fun addActivity(tripId: String, activity: Activity) = FakeTripDataSource.addActivity(tripId, activity)
    
    override fun updateActivity(tripId: String, activity: Activity) = FakeTripDataSource.updateActivity(tripId, activity)
    
    override fun deleteActivity(tripId: String, activityId: String) = FakeTripDataSource.deleteActivity(tripId, activityId)
}
