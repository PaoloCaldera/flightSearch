package com.example.flightsearch.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.flightsearch.R
import com.example.flightsearch.data.Airport
import com.example.flightsearch.data.airports
import com.example.flightsearch.data.favorites
import com.example.flightsearch.ui.theme.FlightSearchTheme

@Composable
fun FlightsFragment(
    flightsFragmentUiState: FlightsFragmentUiState,
    modifier: Modifier
) {
    LazyColumn(modifier = modifier) {
        items(flightsFragmentUiState.flightsUiState) { flight ->
            FlightItem(
                departure = flight.departure,
                destination = flight.destination,
                isFavorite = flight.isFavorite,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.extra_small_padding))
            )
        }
    }
}

@Composable
fun FlightItem(
    departure: Airport,
    destination: Airport,
    isFavorite: Boolean = false,
    modifier: Modifier
) {
    Card(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.medium_padding))
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.weight(1f)
            ) {
                FlightAirportItem(
                    airport = departure,
                    action = stringResource(R.string.depart)
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.flight_search_item_spacer_height)))
                FlightAirportItem(airport = destination, action = stringResource(R.string.arrive))
            }
            if (isFavorite) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .width(dimensionResource(R.dimen.is_favorite_box_width))
                        .padding(start = dimensionResource(R.dimen.small_padding))
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = stringResource(R.string.favorite_icon_content_description)
                    )
                }
            }
        }
    }
}

@Composable
fun FlightAirportItem(airport: Airport, action: String, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .width(dimensionResource(R.dimen.airport_action_box_width))
                .padding(horizontal = dimensionResource(R.dimen.small_padding))
        ) {
            Text(
                text = action,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelSmall
            )
        }
        AirportItem(airport = airport)
    }
}


@Preview
@Composable
fun FlightsFragmentPreview_Favorites() {
    FlightSearchTheme {
        FlightsFragment(
            flightsFragmentUiState = FlightsFragmentUiState(
                flightsUiState = favorites.map { favorite ->
                    FlightUiState(
                        departure = airports.first { it.iataCode == favorite.departureCode },
                        destination = airports.first { it.iataCode == favorite.destinationCode },
                        isFavorite = true
                    )
                }
            ),
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview
@Composable
fun FlightsFragmentPreview_Search() {
    FlightSearchTheme {
        val departure = airports.first { it.iataCode == "BGY" }
        FlightsFragment(
            flightsFragmentUiState = FlightsFragmentUiState(
                flightsUiState = airports.filter { it.iataCode != "BGY" }.map { airport ->
                    FlightUiState(
                        departure = departure,
                        destination = airport,
                        isFavorite = favorites.any {
                            it.departureCode == departure.iataCode && it.destinationCode == airport.iataCode
                        }
                    )
                }
            ),
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview
@Composable
fun FlightItemPreview() {
    FlightSearchTheme {
        FlightItem(
            departure = airports[0],
            destination = airports[1],
            isFavorite = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.extra_small_padding))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AirportItemPreview() {
    FlightSearchTheme {
        AirportItem(
            airport = airports[0],
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = dimensionResource(R.dimen.small_padding)
                )
        )
    }
}


data class FlightsFragmentUiState(
    val flightsUiState: List<FlightUiState>
)

data class FlightUiState(
    val departure: Airport,
    val destination: Airport,
    val isFavorite: Boolean
)