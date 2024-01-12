package com.knowroaming.esim.app.presentation.components.main

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import org.koin.compose.koinInject


@Composable
fun AppSnackbar() {
    SnackbarHost(hostState = koinInject<SnackbarHostState>()) { data ->
        Snackbar(
            snackbarData = data,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        )
    }
}