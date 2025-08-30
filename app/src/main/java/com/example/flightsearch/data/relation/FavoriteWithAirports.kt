package com.example.flightsearch.data.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.flightsearch.data.entity.Airport
import com.example.flightsearch.data.entity.Favorite

data class FavoriteWithAirports(
    @Embedded val favorite: Favorite,
    @Relation(parentColumn = "departureId", entityColumn = "id") val departure: Airport,
    @Relation(parentColumn = "destinationId", entityColumn = "id") val destination: Airport
)