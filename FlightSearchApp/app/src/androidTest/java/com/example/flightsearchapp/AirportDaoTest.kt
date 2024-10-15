package com.example.flightsearchapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.flightsearchapp.data.FlightDatabase
import com.example.flightsearchapp.data.daos.AirportDao
import com.example.flightsearchapp.data.entities.Airport
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AirportDaoTest {
    private lateinit var db: FlightDatabase
    private lateinit var airportDao: AirportDao

    private val airport1 = Airport(
        id = 1,
        iataCode = "OPO",
        name = "Opo name",
        passengers = 10
    )
    private val airport2 = Airport(
        id = 2,
        iataCode = "RTA",
        name = "Rta name gao",
        passengers = 5
    )
    private val airport3 = Airport(
        id = 3,
        iataCode = "SEA",
        name = "Sea name",
        passengers = 11
    )
    private val airport4 = Airport(
        id = 4,
        iataCode = "LOI",
        name = "Loi name gaoper",
        passengers = 2
    )
    private val airport5 = Airport(
        id = 5,
        iataCode = "GAO",
        name = "Geor name",
        passengers = 2
    )

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, FlightDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        airportDao = db.airportDao()
    }

    private suspend fun populateDb() {
        airportDao.insert(airport1)
        airportDao.insert(airport2)
        airportDao.insert(airport3)
        airportDao.insert(airport4)
        airportDao.insert(airport5)
    }

    @Test
    fun airportDao_getAirportsForWithInsideName_validateListResult() = runBlocking {
        populateDb()
        val airports = airportDao.getAirportsFor("gao").first()
        val expect = listOf(
            airport5,
            airport4,
            airport2
        )
        assertEquals(expect, airports)
    }

    @Test
    fun airportDao_getAirportsForWithIataCode_validateListResult() = runBlocking {
        populateDb()
        val airports = airportDao.getAirportsFor("opo").first()
        val expect = listOf(airport1)
        assertEquals(expect, airports)
    }

    @Test
    fun airportDao_getAirportsExcepts_validateListResult() = runBlocking {
        populateDb()
        val airports = airportDao.getAirportsExcepts("SEA").first()
        val expect = listOf(
            airport1,
            airport2,
            airport4,
            airport5
        )
        assertEquals(expect, airports)
    }

    @Test
    fun airportDao_getAirportName_validateNameResult() = runBlocking {
        populateDb()
        val expectName = airport4.name
        val resultName = airportDao.getAirportName(airport4.iataCode)

        assertEquals(expectName, resultName)
    }

}