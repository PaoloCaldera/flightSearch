package com.example.flightsearch.repository

import com.example.flightsearch.data.FlightSearchDao
import com.example.flightsearch.data.entity.Favorite

class FlightSearchDatabaseRepository(private val dao: FlightSearchDao) {
    suspend fun getAllDestinations(departureCode: String) = dao.selectAllDestinations(departureCode)

    suspend fun getFilteredDestinations(input: String) = dao.selectDestinationByInput(input)

    fun getFavorites() = dao.selectAllFavoriteWithAirports()

    suspend fun saveFavorite(favorite: Favorite) = dao.insertFavorite(favorite)

    suspend fun removeFavorite(favorite: Favorite) = dao.deleteFavorite(favorite)
}