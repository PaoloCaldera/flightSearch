package com.example.flightsearch.ui

import androidx.lifecycle.ViewModel
import com.example.flightsearch.data.Airport
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FlightsFragmentViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(FlightsFragmentUiState())
    val uiState: StateFlow<FlightsFragmentUiState> = _uiState.asStateFlow()

}

data class FlightsFragmentUiState(
    val flightsList: List<FlightUiState> = emptyList()
)

data class FlightUiState(
    val departure: Airport,
    val destination: Airport,
    val isFavorite: Boolean
)