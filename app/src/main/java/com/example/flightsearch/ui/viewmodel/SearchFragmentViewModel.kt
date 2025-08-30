package com.example.flightsearch.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.flightsearch.data.support.AirportSupport
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchFragmentViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(SearchFragmentUiState())
    val uiState: StateFlow<SearchFragmentUiState> = _uiState.asStateFlow()
}

data class SearchFragmentUiState(
    val searchList: List<AirportSupport> = emptyList()
)