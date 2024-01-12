package com.knowroaming.esim.app.presentation.components.country

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.knowroaming.esim.app.domain.model.Country
import com.knowroaming.esim.app.presentation.theme.BrandColor
import com.knowroaming.esim.app.presentation.theme.BrandShape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopularCard(
    country: Country, onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxSize(),
        shape = MaterialTheme.shapes.extraSmall,
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {

            Text(
                text = country.name,
                maxLines = 2,
                fontSize = 16.sp,
                lineHeight = 22.4.sp,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 12.dp).weight(3f),
            )

            CountryFlag(
                country = country, modifier = Modifier.fillMaxHeight().clip(
                    shape = BrandShape.TriangleClip,
                ).width(48.dp).border(
                    width = 0.625.dp,
                    shape = BrandShape.TriangleClip,
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            BrandColor.Gray800, Color.Transparent
                        ), endX = (48 * 0.8).toFloat()
                    )
                )
            )
        }
    }
}