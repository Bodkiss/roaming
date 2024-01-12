package com.knowroaming.esim.app.presentation.view.purchase

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.knowroaming.esim.app.presentation.components.button.AppButton
import com.knowroaming.esim.app.presentation.components.main.AppDivider
import com.knowroaming.esim.app.presentation.model.ESimOrderState
import com.knowroaming.esim.app.presentation.theme.BrandColor
import com.knowroaming.esim.app.presentation.theme.BrandSize
import com.knowroaming.esim.app.util.AppRoute
import com.knowroaming.esim.app.util.decodeBase64ToBitmap
import com.knowroaming.esim.app.util.ext.parseDateTimeString
import com.knowroaming.esim.app.util.ext.tryFormatDateTime
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun QRCodeScreen(navigator: Navigator, state: ESimOrderState) {
    LazyColumn {
        item { PurchaseSuccessfulItem() }

        item {

            Surface(
                shadowElevation = BrandSize.md,
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth().padding(horizontal = BrandSize.lg)
                    .padding(bottom = BrandSize.lg)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(BrandSize.lg),
                    verticalArrangement = Arrangement.spacedBy(BrandSize.md),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "QR Code Installation",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight(700)
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(BrandSize.md),
                    )

                    Text(
                        text = "Scan the QR code to activate your eSIM.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight(400),
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(BrandSize.sm),
                    )
                    Spacer(modifier = Modifier.size(BrandSize.lg))

                    state.order?.qrCodeImageBase64.let { data ->
                        when (data) {
                            null -> Image(
                                painter = rememberVectorPainter(Icons.Default.QrCode),
                                contentDescription = "Description of the image",
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier.fillMaxWidth().weight(1f).background(
                                    color = BrandColor.White
                                ).clip(shape = MaterialTheme.shapes.medium)
                            )

                            else -> QrCodeIconImage(decodeBase64ToBitmap(data))
                        }
                    }

                    Spacer(modifier = Modifier.size(BrandSize.lg))

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
fun QrCodeIconImage(bitmap: ImageBitmap, size: Dp = 268.dp) {
    Image(
        bitmap = bitmap,
        contentScale = ContentScale.FillBounds,
        contentDescription = "Description of the image",
        modifier = Modifier.fillMaxWidth().height(size).background(
            color = BrandColor.White, shape = MaterialTheme.shapes.medium
        ).padding(BrandSize.md)
    )
}