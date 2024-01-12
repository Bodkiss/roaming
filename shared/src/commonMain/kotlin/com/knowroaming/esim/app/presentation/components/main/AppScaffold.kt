package com.knowroaming.esim.app.presentation.components.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp

@Composable
fun AppScaffold(
    modifier: Modifier = Modifier,
    topBar: (@Composable () -> Unit) = {},
    bottomBar: (@Composable () -> Unit) = {},
    content: @Composable (padding: PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = modifier.shadow(8.dp),
        topBar = topBar,
        snackbarHost = {
            AppSnackbar()
        },
        bottomBar = bottomBar,
        content = content,
    )
}