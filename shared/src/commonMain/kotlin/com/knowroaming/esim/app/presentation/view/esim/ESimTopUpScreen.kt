package com.knowroaming.esim.app.presentation.view.esim

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.knowroaming.esim.app.domain.data.Destination
import com.knowroaming.esim.app.domain.model.PackagePlan
import com.knowroaming.esim.app.presentation.components.button.AppButton
import com.knowroaming.esim.app.presentation.components.button.AppButtonVariant
import com.knowroaming.esim.app.presentation.components.main.AppDivider
import com.knowroaming.esim.app.presentation.model.PackageListViewModel
import com.knowroaming.esim.app.presentation.theme.BrandSize
import com.knowroaming.esim.app.util.AppRoute
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun TopUpESimScreen(navigator: Navigator, iccid: String, countryCode: String) {

    val viewModel = koinViewModel(vmClass = PackageListViewModel::class)

    val state = viewModel.state.collectAsState()

    LaunchedEffect(countryCode) {
        viewModel.getPackages(Destination(code = countryCode))
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 8.dp),
    ) {
        items(state.value.packages) { product ->
            TopUpESimCard(product) {
                navigator.navigate(
                    "${AppRoute.PRODUCT}/${product.country.code}/${product.packageTypeId}?iccid=${iccid}"
                )
            }
        }
    }
}

@Composable
fun TopUpESimCard(product: PackagePlan, onClick: () -> Unit) {
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


            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Coverage", modifier = Modifier.weight(1f), textAlign = TextAlign.Start
                )
                Text(
                    text = product.country.name,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End
                )
            }

            AppDivider(modifier = Modifier.fillMaxWidth())


            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Data", modifier = Modifier.weight(1f), textAlign = TextAlign.Start
                )
                Text(
                    text = "${product.data} GB",
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
                    text = "${product.validityDays} Days",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End
                )
            }

            AppDivider(modifier = Modifier.fillMaxWidth())

            AppButton(
                onClick = onClick,
                type = AppButtonVariant.Primary,
                text = "Top Up for ${product.currency}${product.price}"
            )
        }
    }
}