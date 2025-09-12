package com.example.flightsearch.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.flightsearch.R
import com.example.flightsearch.data.entity.Airport
import com.example.flightsearch.data.entity.Favorite

@Composable
fun AirportItem(airport: Airport, modifier: Modifier = Modifier) {
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

@Composable
fun FavoriteAlertDialog(
    isAddAction: Boolean,
    favoriteSelected: Favorite,
    onDismiss: () -> Unit,
    onAddFavorite: (Favorite) -> Unit,
    onRemoveFavorite: (Favorite) -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Favorites") },
        text = {
            if (isAddAction)
                Text(text = "Add this flight into your favorites list")
            else
                Text(text = "Remove this flight from your favorites list")
        },
        confirmButton = {
            if (isAddAction) {
                TextButton(onClick = { onAddFavorite(favoriteSelected) }) {
                    Text(text = "Add")
                }
            } else {
                TextButton(onClick = { onRemoveFavorite(favoriteSelected) }) {
                    Text(text = "Remove")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        },
        icon = {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = ""
            )
        }
    )
}