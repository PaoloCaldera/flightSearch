package com.example.flightsearch.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearch.FlightSearchApplication
import com.example.flightsearch.repository.FlightSearchDatabaseRepository
import com.example.flightsearch.repository.FlightSearchPreferencesRepository
import com.example.flightsearch.ui.model.FlightUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class FlightsFragmentViewModel(
    private val preferencesRepository: FlightSearchPreferencesRepository,
    private val roomRepository: FlightSearchDatabaseRepository
) : ViewModel() {

    val uiState: StateFlow<List<FlightUiState>> = preferencesRepository.searchText
        .map { searchText ->
            if (searchText.isEmpty()) {
                roomRepository.getFavorites().map {
                    FlightUiState(
                        departure = roomRepository.getAirportByCode(it.departureCode),
                        destination = roomRepository.getAirportByCode(it.destinationCode),
                        isFavorite = true
                    )
                }
            } else {
                val list = roomRepository.getAllAirports()
                val departure = list.first { it.iataCode == searchText }
                list.filter { it.iataCode != searchText }.map {
                    val isFavorite = roomRepository.getFavoritesByCodes(
                        departureCode = departure.iataCode,
                        destinationCode = it.iataCode
                    ).isNotEmpty()
                    FlightUiState(
                        departure = departure,
                        destination = it,
                        isFavorite = isFavorite
                    )
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FlightSearchApplication)
                FlightsFragmentViewModel(
                    preferencesRepository = application.flightSearchPreferencesRepository,
                    roomRepository = application.flightSearchDatabaseRepository
                )
            }
        }
    }
}