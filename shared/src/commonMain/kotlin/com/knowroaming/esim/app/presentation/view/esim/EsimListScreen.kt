import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.knowroaming.esim.app.domain.data.AssignedEsim
import com.knowroaming.esim.app.domain.model.Customer
import com.knowroaming.esim.app.presentation.components.button.AppButton
import com.knowroaming.esim.app.presentation.components.button.AppButtonVariant
import com.knowroaming.esim.app.presentation.components.country.CountryFlag
import com.knowroaming.esim.app.presentation.components.main.AppDivider
import com.knowroaming.esim.app.presentation.components.main.AppTextButton
import com.knowroaming.esim.app.presentation.model.ESimListViewModel
import com.knowroaming.esim.app.presentation.theme.BrandSize
import com.knowroaming.esim.app.presentation.util.ShimmerEffect
import com.knowroaming.esim.app.util.AppRoute
import com.knowroaming.esim.app.util.ext.parseDateTimeString
import com.knowroaming.esim.app.util.injection.AppKoin
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.daysUntil
import kotlinx.datetime.plus
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.Navigator
import org.koin.compose.koinInject
import org.koin.core.qualifier.named


@Composable
fun EsimListScreen(
    navigator: Navigator
) {
    val viewModel = koinViewModel(vmClass = ESimListViewModel::class)
    val esims by viewModel.esims

    val customer by koinInject<Flow<Customer?>>(named(AppKoin.AUTH_USER)).collectAsState(null)

    LaunchedEffect(customer?.id) {
        customer?.id?.let { viewModel.getCustomerEsimList(it) }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(BrandSize.sm),
        contentPadding = PaddingValues(horizontal = 0.dp, vertical = BrandSize.md),
    ) {

        if (esims.data.isEmpty() && !esims.loading) {
            item {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(BrandSize.lg),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Info icon",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(BrandSize.x6l).padding(BrandSize.md)
                        )
                        Spacer(modifier = Modifier.height(BrandSize.lg))
                        if (customer !== null) {
                            Text("You current have no eSims")
                            Spacer(modifier = Modifier.height(BrandSize.lg))
                            AppTextButton("View all countries") {
                                navigator.navigate(AppRoute.DESTINATIONS)
                            }
                        } else {
                            AppTextButton("Login to view eSims") {
                                navigator.navigate(AppRoute.AUTH)
                            }
                        }
                    }
                }
            }
        }

        if (esims.loading) {
            items(3) {
                Surface(
                    shadowElevation = BrandSize.md,
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.fillMaxWidth().height(328.dp)
                        .padding(vertical = BrandSize.md, horizontal = BrandSize.lg),
                ) {
                    ShimmerEffect(
                        modifier = Modifier.fillParentMaxSize()
                    )
                }
            }
        }

        items(esims.data) { esim ->
            ESimCard(esim, navigator)
        }
    }
}

@Composable
fun ESimCard(esim: AssignedEsim, navigator: Navigator) {
    val validity = remember {
        esim.packages.maxBy {
            it.timeAllowanceDays
        }.let { product ->
            esim.assignedDate.parseDateTimeString()?.let { assigned ->
                val validDate =
                    assigned.date.plus(product.timeAllowanceDays.toInt(), DateTimeUnit.DAY)
                assigned.date.daysUntil(validDate)
            } ?: product.timeAllowanceDays
        }
    }

    Surface(
        shadowElevation = BrandSize.md,
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surface,
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
                    text = "ICCID", modifier = Modifier.weight(1f), textAlign = TextAlign.Start
                )
                Text(
                    text = esim.iccid, fontWeight = FontWeight.Bold, textAlign = TextAlign.End
                )
            }

            AppDivider(modifier = Modifier.fillMaxWidth())


            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Data", modifier = Modifier.weight(1f), textAlign = TextAlign.Start
                )
                Text(
                    text = "${esim.dataUsageRemainingGigabytes} GB",
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
                    text = "$validity Days Remaining",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End
                )
            }

            AppDivider(modifier = Modifier.fillMaxWidth())
            Row {
                AppButton(text = "Top Up",
                    modifier = Modifier.weight(1f),
                    type = AppButtonVariant.Primary,
                    onClick = {
                        navigator.navigate("${AppRoute.ESIM_LIST}/${esim.iccid}/top_up?country_code=${esim.country.code}")
                    })

                Spacer(Modifier.width(BrandSize.lg))
                AppButton(text = "Details",
                    modifier = Modifier.weight(1f),
                    type = AppButtonVariant.Tertiary,
                    onClick = {
                        navigator.navigate("${AppRoute.ESIM_LIST}/${esim.iccid}")
                    })
            }
        }
    }
}