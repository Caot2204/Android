package com.example.flightsearchapp.data.repositories

import com.example.flightsearchapp.data.daos.AirportDao
import com.example.flightsearchapp.data.entities.Airport
import kotlinx.coroutines.flow.Flow

class OfflineAirportRepository(private val airportDao: AirportDao) : AirportRepository {
    override fun getAirportsFor(userInput: String): Flow<List<Airport>> =
        airportDao.getAirportsFor(userInput)

    override fun getAirportsExcepts(iataCode: String): Flow<List<Airport>> =
        airportDao.getAirportsExcepts(iataCode)

    override suspend fun getAirportName(iataCode: String): String =
        airportDao.getAirportName(iataCode)
}