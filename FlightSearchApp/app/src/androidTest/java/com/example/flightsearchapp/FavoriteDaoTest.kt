package com.example.flightsearchapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.flightsearchapp.data.FlightDatabase
import com.example.flightsearchapp.data.daos.FavoriteDao
import com.example.flightsearchapp.data.entities.Favorite
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class FavoriteDaoTest {
    private lateinit var favoriteDao: FavoriteDao
    private lateinit var db: FlightDatabase

    private val favorite1 = Favorite(
        id = 1,
        departureCode = "AIA",
        destinationCode = "ORO"
    )

    private val favorite2 = Favorite(
        id = 2,
        departureCode = "AOP",
        destinationCode = "RTO"
    )

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, FlightDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        favoriteDao = db.favoriteDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun favoriteDao_insertFavorite_validateFavoritesListSize() = runBlocking {
        favoriteDao.insert(favorite1)
        favoriteDao.insert(favorite2)
        val favorites = favoriteDao.getAllFavorites().first()
        assertEquals(2, favorites.size)
    }

    @Test
    @Throws(Exception::class)
    fun favoriteDao_insertFavorite() = runBlocking {
        favoriteDao.insert(favorite1)
        val favorites = favoriteDao.getAllFavorites().first()
        assertEquals(favorite1, favorites[0])
    }

    @Test
    @Throws(Exception::class)
    fun favoriteDao_deleteFavorite_empyFavoriteList() = runBlocking {
        favoriteDao.insert(favorite1)
        favoriteDao.delete(favorite1.departureCode, favorite1.destinationCode)
        val favorites = favoriteDao.getAllFavorites().first()
        assertEquals(emptyList<Favorite>(), favorites)
    }

    @Test
    @Throws(Exception::class)
    fun favoriteDao_searchFavoriteId_validateResult() = runBlocking {
        favoriteDao.insert(favorite1)
        val favorite = favoriteDao.isFavorite("AIA", "ORO")
        assertEquals(true, favorite)
    }

    @Test
    @Throws(Exception::class)
    fun favoriteDao_searchFavoriteIdInExists_validateResultFalse() = runBlocking {
        val favorite = favoriteDao.isFavorite("KFD", "DOK")
        assertFalse(favorite)
    }
}