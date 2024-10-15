package com.example.flightsearchapp.data.repositories

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.IOException

/**
 * Repository for every user's airport search
 */
class UsersSearchesRepository(
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        val USER_SEARCHES = stringPreferencesKey("user_searches")
        const val TAG = "userSearchesRepo"
    }

    val usersSearch: Flow<List<String>> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences.stringList
        }

    suspend fun saveUserSearches(userInput: String) {
        val listToSave: MutableList<String> = usersSearch.first().toMutableList()
        listToSave.add(userInput)
        dataStore.saveStringList(listToSave)
    }

    private val Preferences.stringList: List<String>
        get() = runCatching {
            Json.decodeFromString<List<String>>(this[USER_SEARCHES] ?: "")
        }.getOrDefault(emptyList())

    private suspend fun DataStore<Preferences>.saveStringList(list: List<String>) {
        edit { preferences ->
            preferences[USER_SEARCHES] = Json.encodeToString(list)
        }
    }
}