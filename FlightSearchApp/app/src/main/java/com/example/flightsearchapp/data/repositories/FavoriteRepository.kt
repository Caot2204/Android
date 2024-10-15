package com.example.flightsearchapp.data.repositories

import com.example.flightsearchapp.data.entities.Favorite
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    suspend fun saveFavorite(favorite: Favorite)

    suspend fun deleteFavorite(iataDepart: String, iataArrive: String)

    suspend fun isFavorite(iataDepart: String, iataArrive: String): Boolean

    fun getAllFavorites(): Flow<List<Favorite>>

}