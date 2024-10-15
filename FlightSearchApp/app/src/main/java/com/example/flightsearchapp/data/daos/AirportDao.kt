package com.example.flightsearchapp.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.flightsearchapp.data.entities.Airport
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {

    @Query("SELECT * FROM airport " +
            "WHERE name LIKE '%' || :userInput || '%' " +
            "OR iata_code LIKE '%' || :userInput || '%'" +
            "ORDER BY iata_code")
    fun getAirportsFor(userInput: String): Flow<List<Airport>>

    @Query("SELECT * FROM airport WHERE iata_code != :iataCode ORDER BY passengers DESC")
    fun getAirportsExcepts(iataCode: String): Flow<List<Airport>>

    @Query("SELECT name FROM airport WHERE iata_code = :iataCode")
    suspend fun getAirportName(iataCode: String): String

    //only for populate test database
    @Insert
    suspend fun insert(airport: Airport)

}