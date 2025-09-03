package com.example.flightsearch.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import com.example.flightsearch.R
import com.example.flightsearch.ui.theme.FlightSearchTheme

@Composable
fun SearchBar(
    searchText: String,
    userSelection: String,
    isSearching: Boolean,
    onTextChange: (String) -> Unit,
    onClearIconClick: () -> Unit,
    onBackIconClick: () -> Unit,
    onMicIconClick: () -> Unit,
    onFocus: (Boolean) -> Unit,
    modifier: Modifier
) {
    val isSearching = isSearching
    val localFocusManager = LocalFocusManager.current

    // Handle the focus of the search bar
    LaunchedEffect(isSearching) {
        if (!isSearching) {
            localFocusManager.clearFocus()
        }
    }

    Box(
        modifier = modifier.background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        OutlinedTextField(
            value = if (isSearching) searchText else userSelection,
            onValueChange = { onTextChange(it) },
            textStyle = MaterialTheme.typography.bodyMedium,
            singleLine = true,
            placeholder = {
                Text(
                    text = stringResource(R.string.search_departure_airport),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            leadingIcon = {
                if (isSearching) {
                    IconButton(onClick = onBackIconClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_icon_content_description),
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            },
            trailingIcon = {
                if (isSearching) {
                    IconButton(onClick = onClearIconClick) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = stringResource(R.string.clear_icon_content_description),
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                } else {
                    IconButton(onClick = onMicIconClick) {
                        Icon(
                            imageVector = Icons.Filled.Mic,
                            contentDescription = stringResource(R.string.microphone_icon_content_description),
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            modifier = modifier
                .padding(
                    start = WindowInsets.systemBars.asPaddingValues()
                        .calculateStartPadding(LayoutDirection.Ltr),
                    end = WindowInsets.systemBars.asPaddingValues()
                        .calculateEndPadding(LayoutDirection.Ltr),
                    top = WindowInsets.systemBars.asPaddingValues().calculateTopPadding()
                )
                .onFocusChanged {
                    onFocus(it.isFocused)
                }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    FlightSearchTheme {
        SearchBar(
            searchText = "",
            userSelection = "",
            isSearching = false,
            onTextChange = {},
            onClearIconClick = {},
            onBackIconClick = {},
            onMicIconClick = {},
            onFocus = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}