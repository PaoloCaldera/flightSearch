package com.example.flightsearch.ui

import androidx.compose.foundation.combinedClickable
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.flightsearch.R
import com.example.flightsearch.data.entity.Airport
import com.example.flightsearch.data.entity.Favorite
import com.example.flightsearch.ui.model.FlightUiState
import com.example.flightsearch.ui.theme.FlightSearchTheme

@Composable
fun FlightsFragment(
    flightsList: List<FlightUiState>,
    onAddFavorite: (Favorite) -> Unit,
    onRemoveFavorite: (Favorite) -> Unit,
    modifier: Modifier
) {
    var showDialog by rememberSaveable { mutableStateOf(false) }
    var departureCode by rememberSaveable { mutableStateOf("") }
    var destinationCode by rememberSaveable { mutableStateOf("") }
    var isFavorite by rememberSaveable { mutableStateOf(false) }

    LazyColumn(modifier = modifier.padding(dimensionResource(R.dimen.small_padding))) {
        items(flightsList) { flight ->
            FlightItem(
                flight = flight,
                onFlightLongClicked = {
                    departureCode = flight.departure.iataCode
                    destinationCode = flight.destination.iataCode
                    isFavorite = flight.isFavorite
                    showDialog = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.extra_small_padding))
            )
        }
    }

    if (showDialog) {
        FavoriteAlertDialog(
            isAddAction = !isFavorite,
            favoriteSelected = Favorite(
                departureCode = departureCode,
                destinationCode = destinationCode
            ),
            onDismiss = { showDialog = false },
            onAddFavorite = {
                showDialog = false
                onAddFavorite(it)
            },
            onRemoveFavorite = {
                showDialog = false
                onRemoveFavorite(it)
            }
        )
    }
}

@Composable
fun FlightItem(
    flight: FlightUiState,
    onFlightLongClicked: () -> Unit,
    modifier: Modifier
) {
    Card(
        modifier = modifier.combinedClickable(
            enabled = true,
            onClick = { onFlightLongClicked() },
            onLongClick = { onFlightLongClicked() }
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
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
                    airport = flight.departure,
                    action = stringResource(R.string.depart)
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.flight_search_item_spacer_height)))
                FlightAirportItem(airport = flight.destination, action = stringResource(R.string.arrive))
            }
            if (flight.isFavorite) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .width(dimensionResource(R.dimen.is_favorite_box_width))
                        .padding(start = dimensionResource(R.dimen.small_padding))
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = stringResource(R.string.favorite_icon_content_description),
                        tint = MaterialTheme.colorScheme.tertiary
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
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        AirportItem(airport = airport, color = MaterialTheme.colorScheme.onSecondaryContainer)
    }
}


@Preview
@Composable
fun FlightItemPreview() {
    FlightSearchTheme {
        FlightItem(
            FlightUiState(
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
                isFavorite = true
            ),
            onFlightLongClicked = {},
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
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = dimensionResource(R.dimen.small_padding)
                )
        )
    }
}