package com.tempestgf.threep.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.tempestgf.threep.R
import com.tempestgf.threep.data.repository.TripRepositoryImpl
import com.tempestgf.threep.domain.Activity
import com.tempestgf.threep.domain.Trip
import com.tempestgf.threep.domain.TripRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class TripListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TripRepository = TripRepositoryImpl()
    private val TAG = "TripListViewModel"
    
    private val _trips = MutableStateFlow<List<Trip>>(emptyList())
    val trips: StateFlow<List<Trip>> = _trips.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        Log.d(TAG, "Initializing ViewModel and loading trips")
        loadTrips()
    }

    private fun loadTrips() {
        _trips.value = repository.getTrips()
    }

    fun clearError() {
        _errorMessage.value = null
    }

    private fun validateDateString(dateStr: String): LocalDate? {
        return try {
            LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        } catch (e: DateTimeParseException) {
            Log.e(TAG, "Date parsing error for: $dateStr", e)
            null
        }
    }

    private fun getString(resId: Int, vararg formatArgs: Any): String {
        return getApplication<Application>().getString(resId, *formatArgs)
    }

    // T1.1 CRUD: addTrip, editTrip, deleteTrip
    fun addTrip(title: String, startDate: String, endDate: String, description: String): Boolean {
        Log.d(TAG, "Attempting to add trip: $title")
        if (title.isBlank() || startDate.isBlank() || endDate.isBlank() || description.isBlank()) {
            _errorMessage.value = getString(R.string.error_all_fields_required)
            Log.e(TAG, "Failed to add trip: Empty fields")
            return false
        }
        
        val start = validateDateString(startDate)
        val end = validateDateString(endDate)
        
        if (start == null || end == null) {
            _errorMessage.value = getString(R.string.error_invalid_date_format)
            Log.e(TAG, "Failed to add trip: Invalid date format")
            return false
        }

        // T1.3: Dates must be in the future
        if (start.isBefore(LocalDate.now())) {
            _errorMessage.value = getString(R.string.error_start_date_future)
            Log.e(TAG, "Failed to add trip: Start date in the past")
            return false
        }

        if (start.isAfter(end)) {
            _errorMessage.value = getString(R.string.error_start_before_end)
            Log.e(TAG, "Failed to add trip: Start date after end date")
            return false
        }

        val newTrip = Trip(title = title, startDate = startDate, endDate = endDate, description = description)
        repository.addTrip(newTrip)
        Log.i(TAG, "Successfully added trip: ${newTrip.id}")
        loadTrips()
        return true
    }

    fun editTrip(tripId: String, title: String, startDate: String, endDate: String, description: String): Boolean {
        Log.d(TAG, "Attempting to edit trip: $tripId")
        val start = validateDateString(startDate)
        val end = validateDateString(endDate)
        
        if (start == null || end == null || start.isAfter(end)) {
            _errorMessage.value = getString(R.string.error_invalid_dates)
            Log.e(TAG, "Failed to edit trip: Invalid dates")
            return false
        }

        val existingTrip = repository.getTripById(tripId)
        if (existingTrip != null) {
            repository.editTrip(existingTrip.copy(title = title, startDate = startDate, endDate = endDate, description = description))
            Log.i(TAG, "Successfully updated trip: $tripId")
            loadTrips()
            return true
        }
        return false
    }

    fun deleteTrip(tripId: String) {
        Log.d(TAG, "Deleting trip: $tripId")
        repository.deleteTrip(tripId)
        loadTrips()
        Log.i(TAG, "Trip deleted successfully: $tripId")
    }
    
    fun getTrip(tripId: String): Trip? {
        return repository.getTripById(tripId)
    }

    // T1.2 CRUD: addActivity, updateActivity, deleteActivity
    fun addActivity(tripId: String, title: String, description: String, date: LocalDate, time: LocalTime): Boolean {
        Log.d(TAG, "Attempting to add activity to trip: $tripId")
        if (title.isBlank() || description.isBlank()) {
            _errorMessage.value = getString(R.string.error_title_desc_required)
            Log.e(TAG, "Failed to add activity: Empty fields")
            return false
        }

        if (date.isBefore(LocalDate.now())) {
            _errorMessage.value = getString(R.string.error_activity_date_future)
            Log.e(TAG, "Failed to add activity: Date in the past")
            return false
        }

        val trip = repository.getTripById(tripId)
        if (trip != null) {
            val tripStart = validateDateString(trip.startDate)
            val tripEnd = validateDateString(trip.endDate)
            
            if (tripStart != null && tripEnd != null) {
                if (date.isBefore(tripStart) || date.isAfter(tripEnd)) {
                    _errorMessage.value = getString(R.string.error_activity_within_trip_dates, trip.startDate, trip.endDate)
                    Log.e(TAG, "Failed to add activity: Date out of range")
                    return false
                }
            }
            
            val newActivity = Activity(title = title, description = description, date = date, time = time)
            repository.addActivity(tripId, newActivity)
            Log.i(TAG, "Successfully added activity: ${newActivity.id} to trip: $tripId")
            loadTrips()
            return true
        }
        return false
    }

    fun updateActivity(tripId: String, activity: Activity) {
        Log.d(TAG, "Updating activity: ${activity.id} for trip: $tripId")
        repository.updateActivity(tripId, activity)
        loadTrips()
        Log.i(TAG, "Successfully updated activity: ${activity.id}")
    }

    fun deleteActivity(tripId: String, activityId: String) {
        Log.d(TAG, "Deleting activity: $activityId from trip: $tripId")
        repository.deleteActivity(tripId, activityId)
        loadTrips()
        Log.i(TAG, "Activity deleted successfully: $activityId")
    }
}

