package com.example.flightsearch.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import com.example.flightsearch.data.entity.Airport
import com.example.flightsearch.data.entity.Favorite
import com.example.flightsearch.data.relation.FavoriteWithAirports

@Dao
interface FlightSearchDao {

    @Query("SELECT * FROM airport")
    suspend fun selectAllAirports(): List<Airport>

    @Query("SELECT * FROM airport " +
            "WHERE LOWER(iata_code) LIKE '%' || LOWER(:input) || '%' " +
            "OR LOWER(name) LIKE '%' || LOWER(:input) || '%'")
    suspend fun selectDestinationByInput(input: String): List<Airport>

    @Transaction
    @Query("SELECT * FROM favorite")
    suspend fun selectAllFavoriteWithAirports(): List<FavoriteWithAirports>

    @Query("SELECT * FROM favorite WHERE departure_id = :departureId AND destination_id = :destinationId")
    suspend fun selectFavoriteByDepartureIdAndDestinationId(
        departureId: Int,
        destinationId: Int
    ): List<Favorite>

    @Insert(onConflict = REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)
}