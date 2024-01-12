package com.knowroaming.esim.app.presentation.components.country

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.knowroaming.esim.app.domain.model.Country
import com.knowroaming.esim.app.presentation.theme.BrandSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DestinationCard(
    country: Country, onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxSize(),
        shape = CircleShape,
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(BrandSize.lg),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(BrandSize.md),
            ) {

                CountryFlag(
                    country = country, modifier = Modifier.size(40.dp).clip(
                        shape = CircleShape,
                    )
                )

                Text(
                    text = country.name,
                    maxLines = 2,
                    fontSize = 16.sp,
                    lineHeight = 19.2.sp,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Filled.ArrowForwardIos,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = "Open Packages for ${country.name}"
            )
        }
    }
}