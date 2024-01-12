package com.knowroaming.esim.app.presentation.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import com.knowroaming.esim.app.presentation.theme.BrandSize

@Composable
fun AppIconButton(
    icon: ImageVector,
    description: String,
    size: Dp = BrandSize.xl,
    shape: Shape = CircleShape,
    color: Color = MaterialTheme.colorScheme.onSurface,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    modifier: Modifier = Modifier
        .size(size)
        .background(
            backgroundColor,
            shape = shape
        ),
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        Icon(
            tint = color,
            imageVector = icon,
            contentDescription = description,
            modifier = Modifier
                .fillMaxSize() // Smaller icon size
        )
    }
}