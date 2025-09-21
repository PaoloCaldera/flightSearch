package com.example.flightsearch.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.flightsearch.R
import com.example.flightsearch.data.entity.Airport
import com.example.flightsearch.data.entity.Favorite
import com.example.flightsearch.ui.model.MicUiState

@Composable
fun AirportItem(airport: Airport, modifier: Modifier = Modifier, color: Color) {
    Column(modifier = modifier) {
        Text(
            text = airport.name,
            textAlign = TextAlign.Left,
            lineHeight = dimensionResource(R.dimen.airport_name_line_height).value.sp,
            style = MaterialTheme.typography.bodySmall,
            color = color
        )
        Text(
            text = airport.iataCode,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleSmall,
            color = color
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
        title = { Text(text = stringResource(R.string.alert_dialog_title)) },
        text = {
            if (isAddAction)
                Text(text = stringResource(R.string.alert_dialog_description_add))
            else
                Text(text = stringResource(R.string.alert_dialog_description_remove))
        },
        confirmButton = {
            if (isAddAction) {
                TextButton(onClick = { onAddFavorite(favoriteSelected) }) {
                    Text(
                        text = stringResource(R.string.alert_dialog_confirm_add),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                TextButton(onClick = { onRemoveFavorite(favoriteSelected) }) {
                    Text(
                        text = stringResource(R.string.alert_dialog_confirm_remove),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(R.string.alert_dialog_dismiss),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        icon = {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = stringResource(R.string.favorite_icon_content_description)
            )
        },
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        textContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        iconContentColor = MaterialTheme.colorScheme.tertiary
    )
}


@Composable
fun RecordingAlertDialog(micUiState: MicUiState) {
    if (micUiState.showDialog) {
        AlertDialog(
            onDismissRequest = { },
            text = {
                Text(
                    text = micUiState.dialogText,
                    textAlign = TextAlign.Center
                )
            },
            confirmButton = { },
            dismissButton = { },
            icon = {
                Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = stringResource(R.string.microphone_icon_content_description)
                )
            }
        )
    }
}