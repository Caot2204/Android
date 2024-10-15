package com.example.flightsearchapp.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.flightsearchapp.data.entities.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Insert
    suspend fun insert(favorite: Favorite)

    @Query("DELETE FROM favorite WHERE departure_code = :iataDepart AND destination_code = :iataArrive")
    suspend fun delete(iataDepart: String, iataArrive: String)

    @Query("SELECT * FROM favorite")
    fun getAllFavorites(): Flow<List<Favorite>>

    @Query("SELECT CAST(EXISTS(SELECT 1 FROM favorite WHERE departure_code = :iataDepart AND destination_code = :iataArrive) AS BOOLEAN)")
    suspend fun isFavorite(iataDepart: String, iataArrive: String): Boolean

}