package com.example.flightsearch.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.flightsearch.R
import com.example.flightsearch.data.support.AirportSupport

@Composable
fun AirportItem(airport: AirportSupport, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = airport.name,
            textAlign = TextAlign.Left,
            lineHeight = dimensionResource(R.dimen.airport_name_line_height).value.sp,
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = airport.iataCode,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleSmall
        )
    }
}