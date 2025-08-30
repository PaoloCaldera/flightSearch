package com.example.flightsearch.data.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.flightsearch.data.entity.Airport
import com.example.flightsearch.data.entity.Favorite

data class FavoriteWithAirports(
    @Embedded val favorite: Favorite,
    @Relation(parentColumn = "departure_id", entityColumn = "id") val departure: Airport,
    @Relation(parentColumn = "destination_id", entityColumn = "id") val destination: Airport
)