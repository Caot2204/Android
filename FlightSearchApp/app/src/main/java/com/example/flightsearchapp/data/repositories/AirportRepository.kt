package com.example.flightsearchapp.data.repositories

import com.example.flightsearchapp.data.entities.Airport
import kotlinx.coroutines.flow.Flow

interface AirportRepository {

    fun getAirportsFor(userInput: String): Flow<List<Airport>>

    fun getAirportsExcepts(iataCode: String): Flow<List<Airport>>

    suspend fun getAirportName(iataCode: String): String

}