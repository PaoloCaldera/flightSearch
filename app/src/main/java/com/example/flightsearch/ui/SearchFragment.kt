package com.example.flightsearch.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.flightsearch.R
import com.example.flightsearch.data.entity.Airport
import com.example.flightsearch.ui.theme.FlightSearchTheme

@Composable
fun SearchFragment(
    searchList: List<Airport>,
    onSelection: (String) -> Unit,
    modifier: Modifier
) {
    LazyColumn(modifier = modifier) {
        items(searchList) { airport ->
            AirportItem(
                airport = airport,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.extra_small_padding))
                    .clickable { onSelection(airport.iataCode) }
            )
        }
    }
}

@Preview
@Composable
fun SearchFragmentPreview() {
    FlightSearchTheme {
        SearchFragment(
            searchList = emptyList(),
            onSelection = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}