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
import kotlinx.coroutines.flow.Flow

@Dao
interface FlightSearchDao {

    @Query("SELECT * FROM airport WHERE iata_code != :iataCode")
    suspend fun selectAllDestinations(iataCode: String): List<Airport>

    @Query("SELECT * FROM airport " +
            "WHERE LOWER(iata_code) LIKE '%' || LOWER(:input) || '%' " +
            "OR LOWER(name) LIKE '%' || LOWER(:input) || '%'")
    suspend fun selectDestinationByInput(input: String): List<Airport>

    @Transaction
    @Query("SELECT * FROM favorite")
    fun selectAllFavoriteWithAirports(): Flow<List<FavoriteWithAirports>>

    @Insert(onConflict = REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)
}