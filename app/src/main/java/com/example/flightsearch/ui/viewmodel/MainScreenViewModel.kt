package com.example.flightsearch.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearch.FlightSearchApplication
import com.example.flightsearch.data.entity.Airport
import com.example.flightsearch.data.entity.Favorite
import com.example.flightsearch.repository.FlightSearchDatabaseRepository
import com.example.flightsearch.repository.FlightSearchPreferencesRepository
import com.example.flightsearch.ui.model.FlightUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val preferencesRepository: FlightSearchPreferencesRepository,
    private val roomRepository: FlightSearchDatabaseRepository
) : ViewModel() {

    val userSelection: StateFlow<String> = preferencesRepository.userSelection
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ""
        )

    private val _searchTextUiState = MutableStateFlow("")
    val searchTextUiState: StateFlow<String> = _searchTextUiState.asStateFlow()

    private val _isSearchingUiState = MutableStateFlow(false)
    val isSearchingUiState: StateFlow<Boolean> = _isSearchingUiState.asStateFlow()

    val searchList: StateFlow<List<Airport>> = _searchTextUiState.map {
        roomRepository.getFilteredAirports(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val flightsList: StateFlow<List<FlightUiState>> =
        combine(
            preferencesRepository.userSelection,
            roomRepository.getFavorites()
        ) { selection, favorites ->
            if (selection.isNotEmpty()) {
                val list = roomRepository.getAllAirports()
                val departure = list.first { it.iataCode == selection }
                list.filter { it.iataCode != selection }.map {
                    val isFavorite = favorites.any { favorite ->
                        favorite.departureCode == departure.iataCode && favorite.destinationCode == it.iataCode
                    }
                    FlightUiState(
                        departure = departure,
                        destination = it,
                        isFavorite = isFavorite
                    )
                }
            } else emptyList()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val favoritesList: StateFlow<List<FlightUiState>> =
        roomRepository.getFavorites().map { favorites ->
            favorites.map {
                FlightUiState(
                    departure = roomRepository.getAirportByCode(it.departureCode),
                    destination = roomRepository.getAirportByCode(it.destinationCode),
                    isFavorite = true
                )
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun setSearchText(value: String) {
        _searchTextUiState.value = value
    }

    fun setIsSearching(value: Boolean) {
        _isSearchingUiState.value = value
    }

    fun onUserSelection(iataCode: String) {
        viewModelScope.launch {
            preferencesRepository.saveUserSelectionPreference(iataCode)
        }
        setSearchText(iataCode)
        setIsSearching(false)
    }

    fun clearUserSelection() {
        viewModelScope.launch {
            preferencesRepository.saveUserSelectionPreference("")
        }
        setSearchText("")
    }

    fun addFavorite(favorite: Favorite) {
        viewModelScope.launch {
            roomRepository.saveFavorite(favorite)
        }
    }

    fun removeFavorite(favorite: Favorite) {
        viewModelScope.launch {
            roomRepository.removeFavorite(
                roomRepository.getFavoritesByCodes(
                    departureCode = favorite.departureCode,
                    destinationCode = favorite.destinationCode
                ).first()
            )
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FlightSearchApplication)
                MainScreenViewModel(
                    preferencesRepository = application.flightSearchPreferencesRepository,
                    roomRepository = application.flightSearchDatabaseRepository
                )
            }
        }
    }
}