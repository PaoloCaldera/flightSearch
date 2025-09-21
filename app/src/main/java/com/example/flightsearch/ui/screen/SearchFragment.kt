package com.example.flightsearch.ui.screen

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
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
    LazyColumn(modifier = modifier.padding(dimensionResource(R.dimen.small_padding))) {
        items(searchList) { airport ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.tiny_padding))
            ) {
                AirportItem(
                    airport = airport,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = dimensionResource(R.dimen.extra_small_padding),
                            horizontal = dimensionResource(R.dimen.medium_padding)
                        )
                        .combinedClickable(
                            enabled = true,
                            onClick = { onSelection(airport.iataCode) }
                        )
                )
            }
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