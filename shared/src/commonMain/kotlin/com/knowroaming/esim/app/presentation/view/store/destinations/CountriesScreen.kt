package com.knowroaming.esim.app.presentation.view.store.destinations

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.knowroaming.esim.app.presentation.components.country.DestinationCard
import com.knowroaming.esim.app.presentation.model.CountryListViewModel
import com.knowroaming.esim.app.presentation.util.ShimmerEffect
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun CountriesScreen(navigator: Navigator, isRegion: Boolean = false) {
    val viewModel = koinViewModel(vmClass = CountryListViewModel::class)
    val listState by viewModel.countries

    val countries = listState.data.filter { it.isRegion == isRegion }.filter { it.code != "2A" }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp),
    ) {

        if (countries.isEmpty()) {
            items(16) {
                ShimmerEffect(
                    modifier = Modifier.fillMaxWidth().heightIn(72.dp).clip(
                        shape = CircleShape,
                    )
                )
            }

        } else {
            items(countries, key = { it.code }) { country ->
                DestinationCard(country = country, onClick = {
                    navigator.navigate("/store/destinations/${country.code}?is_region=${country.isRegion}")
                })
            }
        }
    }
}