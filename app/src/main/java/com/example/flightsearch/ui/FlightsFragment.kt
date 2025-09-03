package com.example.flightsearch.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch.R
import com.example.flightsearch.data.entity.Airport
import com.example.flightsearch.ui.theme.FlightSearchTheme
import com.example.flightsearch.ui.viewmodel.FlightsFragmentViewModel

@Composable
fun FlightsFragment(
    flightsFragmentViewModel: FlightsFragmentViewModel = viewModel(factory = FlightsFragmentViewModel.Factory),
    modifier: Modifier
) {
    val uiState by flightsFragmentViewModel.uiState.collectAsState()

    LazyColumn(modifier = modifier) {
        items(uiState) { flight ->
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
fun FlightItemPreview() {
    FlightSearchTheme {
        FlightItem(
            departure = Airport(
                id = 1,
                name = "Francisco Sá Carneiro Airport",
                iataCode = "OPO",
                passengers = 5053134
            ),
            destination = Airport(
                id = 2,
                name = "Stockholm Arlanda Airport",
                iataCode = "ARN",
                passengers = 7494765
            ),
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
            airport = Airport(
                id = 1,
                name = "Francisco Sá Carneiro Airport",
                iataCode = "OPO",
                passengers = 5053134
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = dimensionResource(R.dimen.small_padding)
                )
        )
    }
}