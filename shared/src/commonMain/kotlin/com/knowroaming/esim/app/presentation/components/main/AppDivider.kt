package com.knowroaming.esim.app.presentation.components.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun AppDivider(
    width: Int = 64,
    height: Int = 1,
    modifier: Modifier = Modifier,
    margin: PaddingValues = PaddingValues(0.dp),
) {
    Box(
        modifier.padding(margin).widthIn(width.dp).height(height.dp)
            .background(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
    )
}