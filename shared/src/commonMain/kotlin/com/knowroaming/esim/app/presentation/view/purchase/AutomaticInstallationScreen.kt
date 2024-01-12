package com.knowroaming.esim.app.presentation.view.purchase

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.knowroaming.esim.app.presentation.components.button.AppButton
import com.knowroaming.esim.app.presentation.components.button.AppButtonVariant
import com.knowroaming.esim.app.presentation.components.main.AppDivider
import com.knowroaming.esim.app.presentation.model.ESimOrderState
import com.knowroaming.esim.app.presentation.theme.BrandColor
import com.knowroaming.esim.app.presentation.theme.BrandSize
import com.knowroaming.esim.app.util.AppRoute
import com.knowroaming.esim.app.util.ext.parseDateTimeString
import com.knowroaming.esim.app.util.ext.tryFormatDateTime
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun AutomaticInstallationScreen(navigator: Navigator, state: ESimOrderState) {
    LazyColumn {
        item { PurchaseSuccessfulItem() }
        item {
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = BrandSize.md,
                contentColor = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth().padding(horizontal = BrandSize.lg),
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(BrandSize.lg),
                    verticalArrangement = Arrangement.spacedBy(BrandSize.md),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Automatic Installation",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight(700)
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(BrandSize.md),
                    )

                    Text(
                        text = "Please use provided link below to install this\neSIM on your device",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight(400),
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(BrandSize.sm),
                    )
                    Spacer(modifier = Modifier.size(BrandSize.xl))

                    AppButton(text = "Install eSIM", type = AppButtonVariant.Primary, onClick = {})
                    Spacer(modifier = Modifier.size(BrandSize.xl))

                    AppDivider(modifier = Modifier.fillMaxWidth())

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Order Number",
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Start
                        )
                        state.order?.let {
                            Text(
                                text = it.orderNumber.toString(),
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.End
                            )
                        }
                    }

                    AppDivider(modifier = Modifier.fillMaxWidth())

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Purchase Price",
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Start
                        )
                        state.order?.let {
                            Text(
                                text = "${it.purchaseCurrency}${it.purchasePrice}",
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.End
                            )
                        }
                    }


                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Purchase Date",
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Start
                        )
                        state.order?.let {
                            Text(
                                text = it.orderDate.parseDateTimeString().tryFormatDateTime(),
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.End
                            )
                        }
                    }

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "ICCID",
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Start
                        )
                        state.order?.let {
                            Text(
                                text = it.iccid,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.End
                            )
                        }
                    }

                    Spacer(modifier = Modifier.size(BrandSize.xl))

                    AppButton(text = "My eSIMs", onClick = {
                        navigator.navigate(AppRoute.MY_ESIMS)
                    })
                }
            }
        }

    }
}

@Composable
fun PurchaseSuccessfulItem() {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = BrandColor.Gray500,
        shadowElevation = BrandSize.md,
        contentColor = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = BrandSize.lg, horizontal = BrandSize.lg),
    ) {
        Text(
            text = "Purchase Successful",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight(700)
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(BrandSize.md),
        )
    }
}