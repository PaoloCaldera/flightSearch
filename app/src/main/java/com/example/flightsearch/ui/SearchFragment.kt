package com.example.flightsearch.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch.R
import com.example.flightsearch.ui.theme.FlightSearchTheme

@Composable
fun SearchFragment(
    searchFragmentViewModel: SearchFragmentViewModel = viewModel(),
    modifier: Modifier
) {
    val uiState by searchFragmentViewModel.uiState.collectAsState()

    LazyColumn(modifier = modifier) {
        items(uiState.searchList) { airport ->
            AirportItem(
                airport = airport,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.extra_small_padding))
            )
        }
    }
}

@Preview
@Composable
fun SearchFragmentPreview() {
    FlightSearchTheme {
        SearchFragment(
            modifier = Modifier.fillMaxWidth()
        )
    }
}