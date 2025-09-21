package com.example.flightsearch.ui.screen

import android.Manifest
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch.ui.theme.FlightSearchTheme
import com.example.flightsearch.ui.viewmodel.MainScreenViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainScreen(
    mainScreenViewModel: MainScreenViewModel,
    modifier: Modifier
) {
    // States
    val userSelection by mainScreenViewModel.userSelection.collectAsState()
    val searchText by mainScreenViewModel.searchTextUiState.collectAsState()
    val isSearching by mainScreenViewModel.isSearchingUiState.collectAsState()
    val searchList by mainScreenViewModel.searchList.collectAsState()
    val flightsList by mainScreenViewModel.flightsList.collectAsState()
    val favoritesList by mainScreenViewModel.favoritesList.collectAsState()
    val micUiState by mainScreenViewModel.micUiState.collectAsState()

    // Permissions
    val recordAudioPermissionState = rememberPermissionState(Manifest.permission.RECORD_AUDIO)
    LaunchedEffect(recordAudioPermissionState.status.isGranted) {
        if (recordAudioPermissionState.status.isGranted)
            mainScreenViewModel.updateRecordAudioPermission()
    }

    RecordingAlertDialog(micUiState)

    Scaffold(
        topBar = {
            SearchBar(
                searchText = searchText,
                userSelection = userSelection,
                isSearching = isSearching,
                onTextChange = { mainScreenViewModel.setSearchText(it) },
                onClearIconClick = { mainScreenViewModel.clearUserSelection() },
                onBackIconClick = { mainScreenViewModel.setIsSearching(false) },
                onMicIconClick = {
                    if (recordAudioPermissionState.status.isGranted) {
                        mainScreenViewModel.setIsSearching(true)
                        mainScreenViewModel.startListening()
                    } else {
                        recordAudioPermissionState.launchPermissionRequest()
                    }
                },
                onFocus = { isFocused -> if (isFocused) mainScreenViewModel.setIsSearching(true) },
                modifier = Modifier.fillMaxWidth()
            )
        },
        modifier = modifier
    ) { innerPadding ->
        if (isSearching) {
            SearchFragment(
                searchList = searchList,
                onSelection = { mainScreenViewModel.onUserSelection(it) },
                modifier = modifier.padding(innerPadding)
            )
        } else {
            FlightsFragment(
                flightsList = if (userSelection.isEmpty()) favoritesList else flightsList,
                onAddFavorite = { mainScreenViewModel.addFavorite(it) },
                onRemoveFavorite = { mainScreenViewModel.removeFavorite(it) },
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
                .windowInsetsPadding(WindowInsets.systemBars),
            mainScreenViewModel = viewModel()
        )
    }
}