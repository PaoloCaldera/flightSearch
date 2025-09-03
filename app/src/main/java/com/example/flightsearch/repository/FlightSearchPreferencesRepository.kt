package com.example.flightsearch.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class FlightSearchPreferencesRepository(
    private val flightSearchDataStore: DataStore<Preferences>
) {
    companion object {
        val SEARCH_TEXT = stringPreferencesKey("search_text")
    }

    val searchText: Flow<String> = flightSearchDataStore.data
        .catch {
            if(it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map {
            it[SEARCH_TEXT] ?: ""
        }

    suspend fun saveSearchTextPreference(searchText: String) {
        flightSearchDataStore.edit {
            it[SEARCH_TEXT] = searchText
        }
    }
}