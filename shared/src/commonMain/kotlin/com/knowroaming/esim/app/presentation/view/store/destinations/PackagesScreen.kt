package com.knowroaming.esim.app.presentation.view.store.destinations

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.knowroaming.esim.app.domain.data.Destination
import com.knowroaming.esim.app.domain.model.Country
import com.knowroaming.esim.app.domain.model.PackagePlan
import com.knowroaming.esim.app.presentation.components.button.AppButton
import com.knowroaming.esim.app.presentation.components.button.AppButtonVariant
import com.knowroaming.esim.app.presentation.components.country.CountryFlag
import com.knowroaming.esim.app.presentation.components.main.AppDivider
import com.knowroaming.esim.app.presentation.model.PackageListViewModel
import com.knowroaming.esim.app.presentation.theme.BrandSize
import com.knowroaming.esim.app.util.AppRoute
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun PackagesScreen(
    code: String? = null,
    region: String? = null,
    navigator: Navigator,
    onCountry: (country: Country) -> Unit,
) {
    val viewModel = koinViewModel(vmClass = PackageListViewModel::class)
    val state = viewModel.state.collectAsState()
    val country = state.value.packages.firstOrNull()?.country

    LaunchedEffect(Unit) {
        viewModel.getPackages(Destination(code = code ?: region))
    }

    LaunchedEffect(country) {
        if (country != null) {
            onCountry(country)
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 8.dp),
    ) {
        items(state.value.packages) { bundle ->
            PackageCard(bundle) {
                navigator.navigate("${AppRoute.PRODUCT}/${code}/${bundle.packageTypeId}")
            }
        }
    }
}

@Composable
fun PackageCard(bundle: PackagePlan, onClick: () -> Unit) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = BrandSize.md,
        contentColor = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = BrandSize.md, horizontal = BrandSize.lg),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(BrandSize.lg),
            verticalArrangement = Arrangement.spacedBy(BrandSize.lg)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(BrandSize.lg)
            ) {
                CountryFlag(
                    country = bundle.country,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.width(62.dp).height(40.dp).clip(
                        shape = MaterialTheme.shapes.extraSmall,
                    )
                )

                Text(
                    text = bundle.country.name, style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight(700),
                    )
                )
            }

            AppDivider(modifier = Modifier.fillMaxWidth())

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Data", modifier = Modifier.weight(1f), textAlign = TextAlign.Start
                )
                Text(
                    text = "${bundle.data} GB",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End
                )
            }

            AppDivider(modifier = Modifier.fillMaxWidth())


            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Validity", modifier = Modifier.weight(1f), textAlign = TextAlign.Start
                )
                Text(
                    text = "${bundle.validityDays} Days",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End
                )
            }

            AppDivider(modifier = Modifier.fillMaxWidth())

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Cost", modifier = Modifier.weight(1f), textAlign = TextAlign.Start
                )
                Text(
                    text = "${bundle.currency}${bundle.price}",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End
                )
            }

            AppDivider(modifier = Modifier.fillMaxWidth())

            AppButton(
                text = "Buy Now for ${bundle.currency}${bundle.price}",
                type = AppButtonVariant.Primary,
                onClick = onClick
            )
        }
    }
}