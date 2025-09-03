package com.example.flightsearch.repository

import com.example.flightsearch.data.FlightSearchDao
import com.example.flightsearch.data.entity.Favorite

class FlightSearchDatabaseRepository(private val dao: FlightSearchDao) {
    suspend fun getAllAirports() = dao.selectAllAirports()
    suspend fun getFilteredDestinations(input: String) = dao.selectDestinationByInput(input)
    suspend fun getFavorites() = dao.selectAllFavoriteWithAirports()
    suspend fun getFavoriteByDepartureAndDestination(departureId: Int, destinationId: Int) =
        dao.selectFavoriteByDepartureIdAndDestinationId(
            departureId = departureId,
            destinationId = destinationId
        )
    suspend fun saveFavorite(favorite: Favorite) = dao.insertFavorite(favorite)
    suspend fun removeFavorite(favorite: Favorite) = dao.deleteFavorite(favorite)
}