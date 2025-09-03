package com.example.flightsearch.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.flightsearch.data.entity.Airport
import com.example.flightsearch.data.entity.Favorite

@Dao
interface FlightSearchDao {

    @Query("SELECT * FROM airport")
    suspend fun selectAllAirports(): List<Airport>

    @Query("SELECT * FROM airport " +
            "WHERE LOWER(iata_code) LIKE '%' || LOWER(:input) || '%' " +
            "OR LOWER(name) LIKE '%' || LOWER(:input) || '%'")
    suspend fun selectAirportByInput(input: String): List<Airport>

    @Query("SELECT * FROM airport WHERE iata_code = :iataCode")
    suspend fun selectAirportByCode(iataCode: String): Airport

    @Query("SELECT * FROM favorite")
    suspend fun selectAllFavorites(): List<Favorite>

    @Query("SELECT * FROM favorite WHERE departure_code = :departureCode AND destination_code = :destinationCode")
    suspend fun selectFavoriteByDepartureCodeAndDestinationCode(
        departureCode: String,
        destinationCode: String
    ): List<Favorite>

    @Insert(onConflict = REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)
}