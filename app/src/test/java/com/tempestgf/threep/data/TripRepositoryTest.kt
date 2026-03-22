package com.tempestgf.threep.data

import com.tempestgf.threep.data.fakeDB.FakeTripDataSource
import com.tempestgf.threep.data.repository.TripRepositoryImpl
import com.tempestgf.threep.domain.Activity
import com.tempestgf.threep.domain.Trip
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import java.time.LocalDate
import java.time.LocalTime

class TripRepositoryTest {

    private lateinit var repository: TripRepositoryImpl

    @Before
    fun setup() {
        // Clear singleton state before each test
        FakeTripDataSource.getTrips().forEach {
            FakeTripDataSource.deleteTrip(it.id)
        }
        repository = TripRepositoryImpl()
    }

    @Test
    fun addTripShouldSaveTripSuccessfully() {
        val trip = Trip(title = "Japan", startDate = "01/04/2026", endDate = "15/04/2026", description = "Vacation")
        repository.addTrip(trip)

        val saved = repository.getTripById(trip.id)
        assertNotNull(saved)
        assertEquals("Japan", saved?.title)
    }

    @Test
    fun editTripShouldUpdateExistingTrip() {
        val trip = Trip(title = "Italy", startDate = "01/05/2026", endDate = "10/05/2026", description = "Work")
        repository.addTrip(trip)

        val updatedTrip = trip.copy(title = "Italy - Extended", endDate = "15/05/2026")
        repository.editTrip(updatedTrip)

        val retrieved = repository.getTripById(trip.id)
        assertEquals("Italy - Extended", retrieved?.title)
        assertEquals("15/05/2026", retrieved?.endDate)
    }

    @Test
    fun deleteTripShouldRemoveTripFromRepo() {
        val trip = Trip(title = "Delete Me", startDate = "01/01/2026", endDate = "02/01/2026", description = "Testing delete")
        repository.addTrip(trip)

        assertEquals(1, repository.getTrips().size)
        repository.deleteTrip(trip.id)
        assertEquals(0, repository.getTrips().size)
    }

    @Test
    fun addActivityShouldAddActivityToTrip() {
        val trip = Trip(title = "France", startDate = "01/06/2026", endDate = "10/06/2026", description = "Tour")
        repository.addTrip(trip)

        val activity = Activity(title = "Eiffel Tower", description = "Visit tower", date = LocalDate.of(2026, 6, 2), time = LocalTime.NOON)
        repository.addActivity(trip.id, activity)

        val updatedTrip = repository.getTripById(trip.id)
        assertNotNull(updatedTrip)
        assertEquals(1, updatedTrip?.activities?.size)
        assertEquals("Eiffel Tower", updatedTrip?.activities?.first()?.title)
    }

    @Test
    fun updateActivityShouldModifyExistingActivity() {
        val trip = Trip(title = "Test", startDate = "01/01/2024", endDate = "05/01/2024", description = "...")
        repository.addTrip(trip)
        val act = Activity(title = "A1", description = "d1", date = LocalDate.of(2024,1,1), time = LocalTime.NOON)
        repository.addActivity(trip.id, act)
        val updated = act.copy(title = "A2")
        repository.updateActivity(trip.id, updated)
        val updatedTrip = repository.getTripById(trip.id)
        assertEquals("A2", updatedTrip?.activities?.first()?.title)
    }

    @Test
    fun deleteActivityShouldRemoveActivity() {
        val trip = Trip(title = "Test", startDate = "01/01/2024", endDate = "05/01/2024", description = "...")
        repository.addTrip(trip)
        val act = Activity(title = "A1", description = "d1", date = LocalDate.of(2024,1,1), time = LocalTime.NOON)
        repository.addActivity(trip.id, act)
        repository.deleteActivity(trip.id, act.id)
        val updatedTrip = repository.getTripById(trip.id)
        assertEquals(0, updatedTrip?.activities?.size)
    }
}
