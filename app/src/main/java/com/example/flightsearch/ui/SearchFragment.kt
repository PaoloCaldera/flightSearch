package com.example.flightsearch.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.flightsearch.R
import com.example.flightsearch.data.Airport
import com.example.flightsearch.data.airports
import com.example.flightsearch.ui.theme.FlightSearchTheme

@Composable
fun SearchFragment(
    searchList: List<Airport>,
    modifier: Modifier
) {
    LazyColumn(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = MaterialTheme.shapes.large
            )
            .padding(vertical = dimensionResource(R.dimen.small_padding))
    ) {
        items(searchList) { airport ->
            AirportItem(
                airport = airport,
                modifier = modifier
                    .padding(
                        vertical = dimensionResource(R.dimen.small_padding),
                        horizontal = dimensionResource(R.dimen.search_result_h_padding)
                    )
            )
        }
    }
}

@Preview
@Composable
fun SearchFragmentPreview() {
    FlightSearchTheme {
        SearchFragment(
            searchList = airports.filter {
                it.name.contains("in") || it.iataCode.contains("in")
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}