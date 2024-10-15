package com.example.flightsearchapp.data

import android.content.Context
import com.example.flightsearchapp.data.repositories.AirportRepository
import com.example.flightsearchapp.data.repositories.FavoriteRepository
import com.example.flightsearchapp.data.repositories.OfflineAirportRepository
import com.example.flightsearchapp.data.repositories.OfflineFavoriteRepository

interface AppContainer {
    val airportRepository: AirportRepository
    val favoriteRepository: FavoriteRepository
}

class AppDataContainer(private val context: Context) : AppContainer{

    override val airportRepository: AirportRepository by lazy {
        OfflineAirportRepository(FlightDatabase.getDatabase(context).airportDao())
    }
    override val favoriteRepository: FavoriteRepository by lazy {
        OfflineFavoriteRepository(FlightDatabase.getDatabase(context).favoriteDao())
    }

}