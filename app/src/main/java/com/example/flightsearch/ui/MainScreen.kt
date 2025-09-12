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
    viewModel: MainScreenViewModel = viewModel(factory = MainScreenViewModel.Factory),
    modifier: Modifier
) {
    val userSelection by viewModel.userSelection.collectAsState()
    val searchText by viewModel.searchTextUiState.collectAsState()
    val isSearching by viewModel.isSearchingUiState.collectAsState()
    val searchList by viewModel.searchList.collectAsState()
    val flightsList by viewModel.flightsList.collectAsState()
    val favoritesList by viewModel.favoritesList.collectAsState()

    Scaffold(
        topBar = {
            SearchBar(
                searchText = searchText,
                userSelection = userSelection,
                isSearching = isSearching,
                onTextChange = { viewModel.setSearchText(it) },
                onClearIconClick = { viewModel.clearUserSelection() },
                onBackIconClick = {
                    viewModel.setIsSearching(false)
                },
                onMicIconClick = { },
                onFocus = { isFocused -> if (isFocused) viewModel.setIsSearching(true) },
                modifier = Modifier.fillMaxWidth()
            )
        },
        modifier = modifier
    ) { innerPadding ->
        if (isSearching) {
            SearchFragment(
                searchList = searchList,
                onSelection = { viewModel.onUserSelection(it) },
                modifier = modifier.padding(innerPadding)
            )
        } else {
            FlightsFragment(
                flightsList = if (userSelection.isEmpty()) favoritesList else flightsList,
                onAddFavorite = { viewModel.addFavorite(it) },
                onRemoveFavorite = { viewModel.removeFavorite(it) },
                modifier = modifier.padding(innerPadding)
            )
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