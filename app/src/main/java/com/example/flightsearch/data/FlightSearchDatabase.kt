package com.example.flightsearch.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.flightsearch.data.entity.Airport
import com.example.flightsearch.data.entity.Favorite

@Database(entities = [Airport::class, Favorite::class], version = 1, exportSchema = false)
abstract class FlightSearchDatabase : RoomDatabase() {

    abstract fun dao(): FlightSearchDao

    companion object {
        @Volatile
        var INSTANCE: FlightSearchDatabase? = null

        fun getDatabase(context: Context): FlightSearchDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context = context,
                    klass = FlightSearchDatabase::class.java,
                    name = "flight_search"
                )
                    .createFromAsset("database/flight_search.db")
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}