package com.example.flightsearch.ui.model

import com.example.flightsearch.data.entity.Airport

data class FlightUiState(
    val departure: Airport,
    val destination: Airport,
    val isFavorite: Boolean = false
)
