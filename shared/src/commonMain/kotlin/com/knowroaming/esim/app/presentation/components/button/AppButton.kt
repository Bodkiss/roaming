package com.knowroaming.esim.app.presentation.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.knowroaming.esim.app.presentation.theme.BrandColor
import com.knowroaming.esim.app.presentation.theme.BrandGradient
import com.knowroaming.esim.app.presentation.theme.BrandSize

enum class AppButtonVariant {
    Primary, Secondary, Tertiary, Text, Active, Muted
}

@Composable
fun AppButton(
    text: String,
    size: Dp = 46.dp,
    elevation: Dp = BrandSize.lg,
    enabled: Boolean = true,
    loading: Boolean = false,
    style: TextStyle? = null,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.extraSmall,
    type: AppButtonVariant = AppButtonVariant.Tertiary,
    onClick: () -> Unit,
) {

    val contentColor = when (type) {
        AppButtonVariant.Secondary -> MaterialTheme.colorScheme.onPrimary
        else -> MaterialTheme.colorScheme.onSurface
    }

    val containerColor = when (type) {
        AppButtonVariant.Muted -> BrandColor.GrayMuted
        AppButtonVariant.Secondary -> MaterialTheme.colorScheme.primary
        in listOf(AppButtonVariant.Primary, AppButtonVariant.Active) -> Color.Transparent
        else -> MaterialTheme.colorScheme.surface
    }

    val border = when (type) {
        in listOf(AppButtonVariant.Primary, AppButtonVariant.Active, AppButtonVariant.Muted) -> null
        else -> BorderStroke(
            width = 1.dp,
            color = contentColor,
        )
    }

    val colors = ButtonDefaults.buttonColors(
        containerColor = containerColor,
        contentColor = contentColor,
    )

    Button(
        shape = shape,
        border = border,
        colors = colors,
        onClick = onClick,
        enabled = !loading && enabled,
        contentPadding = PaddingValues(),
        modifier = modifier.fillMaxWidth().clip(shape).shadow(
            elevation,
            ambientColor = MaterialTheme.colorScheme.background,
            spotColor = MaterialTheme.colorScheme.background
        ).heightIn(min = size, max = size).let {
            when (type) {
                AppButtonVariant.Primary -> it.background(
                    brush = BrandGradient.Primary
                )

                AppButtonVariant.Active -> it.background(
                    brush = BrandGradient.Navigation
                )

                else -> it
            }
        },
    ) {
        if (loading) {
            CircularProgressIndicator(
                modifier = modifier.size(size).padding(BrandSize.md), color = contentColor
            )
        } else {
            Text(
                text = text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth(),
                style = style ?: MaterialTheme.typography.labelLarge.copy(
                    fontSize = 16.sp,
                    lineHeight = 19.2.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight(400),
                ),
            )
        }
    }
}