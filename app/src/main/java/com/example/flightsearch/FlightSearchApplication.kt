package com.example.flightsearch

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.flightsearch.data.FlightSearchDatabase
import com.example.flightsearch.repository.FlightSearchDatabaseRepository
import com.example.flightsearch.repository.FlightSearchPreferencesRepository

class FlightSearchApplication : Application() {

    companion object {
        private const val PREFERENCES_NAME = "flight_search_preferences"
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)
    }

    lateinit var flightSearchPreferencesRepository: FlightSearchPreferencesRepository
    lateinit var flightSearchDatabaseRepository: FlightSearchDatabaseRepository

    override fun onCreate() {
        super.onCreate()
        flightSearchPreferencesRepository = FlightSearchPreferencesRepository(dataStore)
        flightSearchDatabaseRepository = FlightSearchDatabaseRepository(
            FlightSearchDatabase.getDatabase(this).dao()
        )
    }
}