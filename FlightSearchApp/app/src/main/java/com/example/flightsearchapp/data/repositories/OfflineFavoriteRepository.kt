package com.example.flightsearchapp.data.repositories

import com.example.flightsearchapp.data.daos.FavoriteDao
import com.example.flightsearchapp.data.entities.Favorite
import kotlinx.coroutines.flow.Flow

class OfflineFavoriteRepository(private val favoriteDao: FavoriteDao) : FavoriteRepository {
    override suspend fun saveFavorite(favorite: Favorite) =
        favoriteDao.insert(favorite)

    override suspend fun deleteFavorite(iataDepart: String, iataArrive: String) =
        favoriteDao.delete(iataDepart, iataArrive)

    override suspend fun isFavorite(iataDepart: String, iataArrive: String): Boolean =
        favoriteDao.isFavorite(iataDepart, iataArrive)

    override fun getAllFavorites(): Flow<List<Favorite>> =
        favoriteDao.getAllFavorites()
}