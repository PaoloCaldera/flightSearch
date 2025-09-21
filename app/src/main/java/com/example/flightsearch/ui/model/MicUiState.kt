package com.example.flightsearch.ui.model

data class MicUiState(
    val showDialog: Boolean = false,
    val dialogText: String = ""
) {
    fun reset(): MicUiState {
        return MicUiState()
    }
}
