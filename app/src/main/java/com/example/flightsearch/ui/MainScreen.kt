package com.example.flightsearch.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch.ui.theme.FlightSearchTheme
import com.example.flightsearch.ui.viewmodel.MainScreenViewModel

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = viewModel(),
    modifier: Modifier
) {
    val mainScreenUiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            SearchBar(
                uiState = mainScreenUiState,
                onTextChange = { viewModel.editSearchText(it) },
                onClearIconClick = { viewModel.editSearchText("") },
                onBackIconClick = { viewModel.unsetSearchingMode() },
                onMicIconClick = { },
                onFocus = { viewModel.setSearchingMode(it) },
                modifier = Modifier.fillMaxWidth()
            )
        },
        modifier = modifier
    ) { innerPadding ->
        if (mainScreenUiState.isSearching) {
            SearchFragment(modifier = modifier.padding(innerPadding))
        } else {
            FlightsFragment(modifier = modifier.padding(innerPadding))
        }
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