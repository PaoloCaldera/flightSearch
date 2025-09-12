package com.example.flightsearch.repository

import com.example.flightsearch.data.FlightSearchDao
import com.example.flightsearch.data.entity.Favorite

class FlightSearchDatabaseRepository(private val dao: FlightSearchDao) {
    suspend fun getAllAirports() = dao.selectAllAirports()
    suspend fun getFilteredAirports(input: String) = dao.selectAirportByInput(input)
    suspend fun getAirportByCode(iataCode: String) = dao.selectAirportByCode(iataCode)
    fun getFavorites() = dao.selectAllFavorites()
    suspend fun getFavoritesByCodes(departureCode: String, destinationCode: String) =
        dao.selectFavoritesByDepartureCodeAndDestinationCode(
            departureCode = departureCode,
            destinationCode = destinationCode
        )
    suspend fun saveFavorite(favorite: Favorite) = dao.insertFavorite(favorite)
    suspend fun removeFavorite(favorite: Favorite) = dao.deleteFavorite(favorite)
}