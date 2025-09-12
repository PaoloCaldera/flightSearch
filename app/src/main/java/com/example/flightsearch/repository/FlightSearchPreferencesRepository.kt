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
        val USER_SELECTION = stringPreferencesKey("user_selection")
    }

    val userSelection: Flow<String> = flightSearchDataStore.data
        .catch {
            if(it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map {
            it[USER_SELECTION] ?: ""
        }

    suspend fun saveUserSelectionPreference(searchText: String) {
        flightSearchDataStore.edit {
            it[USER_SELECTION] = searchText
        }
    }
}