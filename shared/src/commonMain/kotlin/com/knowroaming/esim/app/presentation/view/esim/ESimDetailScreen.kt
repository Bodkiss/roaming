package com.knowroaming.esim.app.presentation.view.esim

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.knowroaming.esim.app.domain.data.AssignedEsim
import com.knowroaming.esim.app.domain.data.PackageDetail
import com.knowroaming.esim.app.domain.model.Customer
import com.knowroaming.esim.app.presentation.components.button.AppButton
import com.knowroaming.esim.app.presentation.components.button.AppButtonVariant
import com.knowroaming.esim.app.presentation.components.country.CountryFlag
import com.knowroaming.esim.app.presentation.components.main.AppDivider
import com.knowroaming.esim.app.presentation.model.ESimListViewModel
import com.knowroaming.esim.app.presentation.theme.BrandSize
import com.knowroaming.esim.app.util.injection.AppKoin
import kotlinx.coroutines.flow.Flow
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.Navigator
import org.koin.compose.koinInject
import org.koin.core.qualifier.named


@Composable
fun ESimDetailScreen(
    iccid: String, navigator: Navigator
) {
    val viewModel = koinViewModel(vmClass = ESimListViewModel::class)
    val esims by viewModel.esims

    val customer by koinInject<Flow<Customer?>>(named(AppKoin.AUTH_USER)).collectAsState(null)

    LaunchedEffect(customer?.id) {
        customer?.id?.let { viewModel.getCustomerEsimList(it) }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 8.dp),
    ) {
        esims.data.filter { it.iccid == iccid }.forEach { esim ->
            esim.packages.forEach { detail ->
                item {
                    ESimPackageDetailCard(detail, esim, navigator)
                }
            }
        }
    }
}

@Composable
fun ESimPackageDetailCard(
    detail: PackageDetail, esim: AssignedEsim, navigator: Navigator
) {

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
                    country = esim.country,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.width(62.dp).height(40.dp).clip(
                        shape = MaterialTheme.shapes.extraSmall,
                    )
                )

                Text(
                    text = esim.country.name, style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight(700),
                    )
                )
            }
            AppDivider(modifier = Modifier.fillMaxWidth())

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Status", modifier = Modifier.weight(1f), textAlign = TextAlign.Start
                )
                Text(
                    text = detail.status, fontWeight = FontWeight.Bold, textAlign = TextAlign.End
                )
            }
            AppDivider(modifier = Modifier.fillMaxWidth())

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "ICCID", modifier = Modifier.weight(1f), textAlign = TextAlign.Start
                )
                Text(
                    text = esim.iccid,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End
                )
            }

            AppDivider(modifier = Modifier.fillMaxWidth())

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Data Used", modifier = Modifier.weight(1f), textAlign = TextAlign.Start
                )
                Text(
                    text = "${detail.dataAllowanceGigabytes} GB",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End
                )
            }

            AppDivider(modifier = Modifier.fillMaxWidth())


            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Data Remaining",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Start
                )
                Text(
                    text = "${detail.dataUsageRemainingGigabytes} GB",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End
                )
            }

            AppDivider(modifier = Modifier.fillMaxWidth())

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Activate On (UTC)",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Start
                )
                Text(
                    text = detail.windowActivationStartUtc,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End
                )
            }

            AppDivider(modifier = Modifier.fillMaxWidth())

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Expires On (UTC)",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Start
                )
                Text(
                    text = detail.windowActivationEndUtc,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End
                )
            }

            AppDivider(modifier = Modifier.fillMaxWidth())

            AppButton(text = "Back to My eSIMs",
                type = AppButtonVariant.Primary,
                onClick = { navigator.goBack() })
        }
    }
}