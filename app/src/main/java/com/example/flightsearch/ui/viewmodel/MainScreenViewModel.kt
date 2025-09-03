package com.example.flightsearch.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearch.FlightSearchApplication
import com.example.flightsearch.repository.FlightSearchPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val preferencesRepository: FlightSearchPreferencesRepository
) : ViewModel() {

    // Get searchTextUiState
    val searchTextUiState: StateFlow<String> = preferencesRepository.searchText
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ""
        )

    // Set searchTextUiState
    fun setSearchText(searchText: String) {
        viewModelScope.launch {
            preferencesRepository.saveSearchTextPreference(searchText)
        }
    }

    private val _isSearchingUiState = MutableStateFlow(false)
    val isSearchingUiState: StateFlow<Boolean> = _isSearchingUiState.asStateFlow()


    fun setIsSearching(value: Boolean) {
        _isSearchingUiState.value = value
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FlightSearchApplication)
                MainScreenViewModel(application.flightSearchPreferencesRepository)
            }
        }
    }
}