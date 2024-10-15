package com.example.flightsearchapp

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.flightsearchapp.data.AppDataContainer
import com.example.flightsearchapp.data.repositories.UsersSearchesRepository

private const val USERS_SEARCH_NAME = "users_search"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = USERS_SEARCH_NAME
)

class FlightSearchApplication : Application() {
    lateinit var container: AppDataContainer
    lateinit var usersSearchesRepository: UsersSearchesRepository

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
        usersSearchesRepository = UsersSearchesRepository(dataStore)
    }
}