package com.example.flightsearch.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainScreenViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MainScreenUiState())
    val uiState: StateFlow<MainScreenUiState> = _uiState.asStateFlow()

    fun setSearchingMode(isFocused: Boolean) {
        if (isFocused)
            _uiState.update { it.copy(isSearching = true) }
    }

    fun editSearchText(text: String) {
        _uiState.update { it.copy(searchText = text) }
    }

    fun unsetSearchingMode() {
        _uiState.update { it.copy(isSearching = false) }
    }
}


data class MainScreenUiState(
    val searchText: String = "",
    val isSearching: Boolean = false
)