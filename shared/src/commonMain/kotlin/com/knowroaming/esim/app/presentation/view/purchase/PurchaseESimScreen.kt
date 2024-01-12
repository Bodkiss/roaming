package com.knowroaming.esim.app.presentation.view.purchase

import AppCheckBox
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.knowroaming.esim.app.domain.data.Destination
import com.knowroaming.esim.app.domain.data.PaymentRequest
import com.knowroaming.esim.app.domain.model.Customer
import com.knowroaming.esim.app.domain.model.Customer.Companion.details
import com.knowroaming.esim.app.domain.repository.ESimRepository
import com.knowroaming.esim.app.presentation.components.country.CountryFlag
import com.knowroaming.esim.app.presentation.components.main.AppDivider
import com.knowroaming.esim.app.presentation.model.PackageListViewModel
import com.knowroaming.esim.app.presentation.theme.BrandSize
import com.knowroaming.esim.app.presentation.util.ShimmerEffect
import com.knowroaming.esim.app.presentation.view.payment.PaymentResult
import com.knowroaming.esim.app.presentation.view.payment.StripePaymentSheet
import com.knowroaming.esim.app.util.AppRoute
import com.knowroaming.esim.app.util.Response
import com.knowroaming.esim.app.util.injection.AppKoin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.Navigator
import org.koin.compose.koinInject
import org.koin.core.qualifier.named


data class PurchaseESimScreenState(
    val isLoading: Boolean = false,
    val compatibilityAccepted: Boolean = false,
    val termAndPolicyAccepted: Boolean = false
) {
    val isAccepted
        get() = compatibilityAccepted && termAndPolicyAccepted
}

@Composable
fun PurchaseESimScreen(
    navigator: Navigator, iccid: String? = null, countryCode: String, packageTypeId: Int
) {
    val viewModel = koinViewModel(vmClass = PackageListViewModel::class)

    LaunchedEffect(Unit) {
        viewModel.getPackages(Destination(code = countryCode))
    }

    val product by viewModel.getPackageById(packageTypeId).collectAsState(null)

    val repository = koinInject<ESimRepository>()
    val snackbar = koinInject<SnackbarHostState>()

    val authenticated by koinInject<Flow<Customer?>>(named(AppKoin.AUTH_USER)).collectAsState(
        null
    )

    val coroutineScope = rememberCoroutineScope()

    val (screenState, setState) = remember { mutableStateOf(PurchaseESimScreenState()) }


    LazyColumn {
        item {

            if (product == null) {
                ShimmerEffect(
                    modifier = Modifier.padding(BrandSize.lg).fillMaxSize()
                )
            } else {
                Surface(
                    shadowElevation = BrandSize.md,
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.fillMaxWidth().padding(BrandSize.lg),
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(BrandSize.lg),
                        verticalArrangement = Arrangement.spacedBy(BrandSize.lg),
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            product?.country?.let {
                                CountryFlag(
                                    country = it,
                                    contentScale = ContentScale.FillBounds,
                                    modifier = Modifier.fillMaxWidth().height(180.dp).clip(
                                        shape = MaterialTheme.shapes.extraSmall,
                                    )
                                )
                            }

                        }

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "Coverage",
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Start
                            )
                            product?.country?.name?.let {
                                Text(
                                    text = it,
                                    maxLines = 1,
                                    fontWeight = FontWeight.Bold,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.End
                                )
                            }
                        }

                        AppDivider(modifier = Modifier.fillMaxWidth())


                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "Data",
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Start
                            )
                            Text(
                                text = "${product?.data} GB",
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.End
                            )
                        }

                        AppDivider(modifier = Modifier.fillMaxWidth())

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "Validity",
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Start
                            )
                            Text(
                                text = "${product?.validityDays} Days",
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.End
                            )
                        }

                        AppDivider(modifier = Modifier.fillMaxWidth())

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "Cost",
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Start
                            )
                            Text(
                                text = "${product?.currency}${product?.price}",
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.End
                            )
                        }

                        AppDivider(modifier = Modifier.fillMaxWidth())

                        Column(
                            verticalArrangement = Arrangement.spacedBy(BrandSize.md),

                            ) {
                            Text(
                                text = "This is Data Only eSim",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                lineHeight = 14.sp,
                                modifier = Modifier.padding(top = BrandSize.sm) // Adjust this padding

                            )

                            Text(
                                text = "The validity period starts when the eSIM connects to any supported network.",
                                fontSize = 12.sp,
                                letterSpacing = (-0.02).em,
                                lineHeight = 14.sp,
                                modifier = Modifier.padding(vertical = BrandSize.xxs) // Reduced vertical padding

                            )


                            Row(verticalAlignment = Alignment.CenterVertically) {
                                AppCheckBox(
                                    checked = screenState.termAndPolicyAccepted,
                                    onCheckedChange = {
                                        setState(
                                            screenState.copy(
                                                termAndPolicyAccepted = it
                                            )
                                        )
                                    },
                                )
                                Text(
                                    text = "By completing the order, you agree to our Terms & Conditions and Privacy Policy.",
                                    fontSize = 12.sp,
                                    lineHeight = 14.sp,
                                    letterSpacing = (-0.02).em

                                )
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                AppCheckBox(
                                    checked = screenState.compatibilityAccepted,
                                    onCheckedChange = {
                                        setState(
                                            screenState.copy(
                                                compatibilityAccepted = it
                                            )
                                        )
                                    },
                                )
                                Text(
                                    text = "Before completing this order, please confirm your device is eSIM compatible and network-unlocked.",
                                    fontSize = 12.sp,
                                    lineHeight = 14.sp,
                                    letterSpacing = (-0.02).em
                                )
                            }
                        }

                        StripePaymentSheet(enabled = screenState.isAccepted,
                            isLoading = screenState.isLoading,
                            onRequest = { callback ->
                                coroutineScope.launch {
                                    val customer = authenticated ?: (navigator.navigateForResult(
                                        AppRoute.AUTH,
                                    ) as Customer?)

                                    if (customer !== null) {
                                        val type = when (iccid) {
                                            null -> PaymentRequest.Type.BUY
                                            else -> PaymentRequest.Type.TOP_UP
                                        }

                                        val response = repository.getEsimPaymentIntent(
                                            PaymentRequest(
                                                type = type,
                                                iccid = iccid,
                                                customer = customer.details(),
                                                packageTypeId = packageTypeId,
                                                paymentMethod = PaymentRequest.Method.STRIPE_INTENT,
                                            )
                                        )

                                        response.collect { result ->
                                            when (result) {
                                                Response.Loading -> setState(
                                                    screenState.copy(
                                                        isLoading = true
                                                    )
                                                )

                                                is Response.Success -> callback(result.data)
                                                is Response.Error -> result.message?.let {
                                                    snackbar.showSnackbar(
                                                        it
                                                    )
                                                }
                                            }

                                            if (result !is Response.Loading) {
                                                setState(screenState.copy(isLoading = false))
                                            }
                                        }
                                    }
                                }
                            }) { result, orderId ->
                            coroutineScope.launch {
                                when (result) {
                                    PaymentResult.Canceled -> {
                                        snackbar.showSnackbar("Payment Canceled")
                                    }

                                    PaymentResult.Completed -> {
                                        snackbar.showSnackbar("Payment Successful")
                                        navigator.navigate("/store/product/${countryCode}/${packageTypeId}/purchased?order_uuid=$orderId")
                                    }

                                    is PaymentResult.Failed -> {
                                        snackbar.showSnackbar(result.error)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
