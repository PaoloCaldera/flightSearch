package com.example.flightsearch.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
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
        item {
            SearchBar(modifier = modifier)
        }
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

@Composable
fun SearchBar(modifier: Modifier) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        textStyle = MaterialTheme.typography.bodyMedium,
        placeholder = {
            Text(
                text = stringResource(R.string.search_departure_airport),
                style = MaterialTheme.typography.bodyMedium
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(R.string.search_icon_content_description)
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = stringResource(R.string.clear_icon_content_description)
            )
        },
        colors = TextFieldDefaults.colors(
            errorIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        modifier = modifier
    )
}


@Preview
@Composable
fun SearchFragmentPreview() {
    FlightSearchTheme {
        SearchFragment(
            searchList = airports.filter { it.iataCode.contains("in") || it.name.contains("in") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun SearchBarPreview() {
    FlightSearchTheme {
        SearchBar(modifier = Modifier.fillMaxWidth())
    }
}