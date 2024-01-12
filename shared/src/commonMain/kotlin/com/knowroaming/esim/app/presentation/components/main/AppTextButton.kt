package com.knowroaming.esim.app.presentation.components.main

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp

@Composable
fun AppTextButton(
    text: String,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    modifier: Modifier = Modifier,
    role: Role = Role.Button,
    onClick: () -> Unit,
) {
    Text(
        text = text,
        modifier = modifier.clickable(
            onClick = onClick,
            role = role
        ),
        style = MaterialTheme.typography.labelMedium.copy(
            color = textColor,
            fontSize = 16.sp,
            lineHeight = 19.2.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight(400),
            textDecoration = TextDecoration.Underline,
        ),
    )
}
