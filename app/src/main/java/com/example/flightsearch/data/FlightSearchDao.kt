package com.example.flightsearch.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.flightsearch.data.entity.Airport
import com.example.flightsearch.data.entity.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface FlightSearchDao {

    @Query("SELECT * FROM airport ORDER BY passengers DESC")
    suspend fun selectAllAirports(): List<Airport>

    @Query(
        "SELECT * FROM airport " +
                "WHERE LOWER(iata_code) LIKE '%' || LOWER(:input) || '%' " +
                "OR LOWER(name) LIKE '%' || LOWER(:input) || '%'" +
                "ORDER BY passengers DESC LIMIT 10"
    )
    suspend fun selectAirportByInput(input: String): List<Airport>

    @Query("SELECT * FROM airport WHERE iata_code = :iataCode")
    suspend fun selectAirportByCode(iataCode: String): Airport

    @Query("SELECT * FROM favorite ORDER BY id DESC")
    fun selectAllFavorites(): Flow<List<Favorite>>

    @Query("SELECT * FROM favorite WHERE departure_code = :departureCode AND destination_code = :destinationCode")
    suspend fun selectFavoritesByDepartureCodeAndDestinationCode(
        departureCode: String,
        destinationCode: String
    ): List<Favorite>

    @Insert(onConflict = REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)
}