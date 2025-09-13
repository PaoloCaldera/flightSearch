package com.example.flightsearch.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Permission(
    permissionState: PermissionState,
    onPermissionGranted: () -> Unit
) {
    LaunchedEffect(permissionState.status) {
        when {
            permissionState.status.isGranted -> onPermissionGranted()
        }
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionWithRationale(
    permissionState: PermissionState,
    onPermissionGranted: () -> Unit,
    imageVector: ImageVector,
    contentDescription: String
) {
    var showRationale by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(permissionState.status) {
        when {
            permissionState.status.isGranted -> onPermissionGranted()
            permissionState.status.shouldShowRationale -> { showRationale = true }
        }
    }

    if (showRationale) {
        AlertDialog(
            onDismissRequest = { showRationale = false },
            text = { Text(text = "Grant the permission in order to use the app functionality.") },
            confirmButton = {
                TextButton(onClick = {
                    showRationale = false
                    permissionState.launchPermissionRequest()
                }) {
                    Text(text = "Grant")
                }
            },
            dismissButton = {
                TextButton(onClick = { showRationale = false }) {
                    Text(text = "Cancel")
                }
            },
            icon = {
                Icon(imageVector = imageVector, contentDescription = contentDescription)
            }
        )
    }
}