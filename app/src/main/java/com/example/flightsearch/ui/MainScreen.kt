package com.example.flightsearch.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.flightsearch.data.Airport
import com.example.flightsearch.ui.theme.FlightSearchTheme

@Composable
fun MainScreen(modifier: Modifier) {
    var isSearching by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            SearchBar(
                isSearching = isSearching,
                onFocus = { isFocused ->
                    if (isFocused) isSearching = true
                },
                modifier = Modifier.fillMaxWidth()
            )
        },
        modifier = modifier
    ) { innerPadding ->
        FlightsFragment(
            flightsList = emptyList(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        )
    }
}


@Preview
@Composable
fun MainScreenPreview() {
    FlightSearchTheme {
        MainScreen(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars)
        )
    }
}

data class FlightUiState(
    val departure: Airport,
    val destination: Airport,
    val isFavorite: Boolean
)