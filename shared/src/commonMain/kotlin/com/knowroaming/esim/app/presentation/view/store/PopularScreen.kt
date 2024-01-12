package com.knowroaming.esim.app.presentation.view.store

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.knowroaming.esim.app.presentation.components.button.AppButton
import com.knowroaming.esim.app.presentation.components.country.PopularCard
import com.knowroaming.esim.app.presentation.model.CountryListViewModel
import com.knowroaming.esim.app.presentation.theme.BrandColor
import com.knowroaming.esim.app.presentation.theme.BrandSize
import com.knowroaming.esim.app.presentation.util.ShimmerEffect
import com.knowroaming.esim.app.util.AppRoute
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource


@OptIn(ExperimentalResourceApi::class)
@Composable
fun PopularScreen(navigator: Navigator) {
    val viewModel = koinViewModel(vmClass = CountryListViewModel::class)

    val popular by viewModel.popular

    Column(
        modifier = Modifier.fillMaxSize().padding(BrandSize.lg),
        verticalArrangement = Arrangement.spacedBy(BrandSize.lg),
    ) {
        Text(
            text = "Popular Countries:",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight(700),
                fontSize = 20.sp,
                lineHeight = 24.sp,
                color = MaterialTheme.colorScheme.onBackground,
            ),
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier.fillMaxWidth().weight(1f),
            verticalArrangement = Arrangement.spacedBy(BrandSize.lg),
            horizontalArrangement = Arrangement.spacedBy(BrandSize.lg),
        ) {
            if (popular.loading) {
                items(16) {
                    ShimmerEffect(
                        modifier = Modifier.fillMaxWidth().height(56.dp)
                    )
                }
            } else {
                items(popular.data, key = { it.code }) { country ->
                    PopularCard(country = country, onClick = {
                        navigator.navigate("/store/destinations/${country.code}")
                    })
                }
            }
        }

        AppButton(text = "View All Countries", onClick = {
            navigator.navigate(AppRoute.DESTINATIONS)
        })

        Box(
            modifier = Modifier.height(84.dp).fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                contentScale = ContentScale.FillBounds,
                painter = painterResource("visa_banner.jpeg"),
                contentDescription = "Know Roaming Banner",
                modifier = Modifier.fillMaxSize().clip(MaterialTheme.shapes.extraSmall),
            )

            Text(
                maxLines = 2,
                modifier = Modifier.padding(16.dp).width(190.dp).align(Alignment.CenterEnd),
                text = "Unlock Incredible Rewards with VISA",
                color = BrandColor.White,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 16.sp,
                    lineHeight = 19.2.sp,
                    fontWeight = FontWeight(400),
                    textAlign = TextAlign.Right,
                )
            )
        }
    }
}