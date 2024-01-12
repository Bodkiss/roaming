package com.knowroaming.esim.app.presentation.view.purchase

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.knowroaming.esim.app.presentation.components.button.AppButton
import com.knowroaming.esim.app.presentation.model.ESimOrderState
import com.knowroaming.esim.app.presentation.theme.BrandColor
import com.knowroaming.esim.app.presentation.theme.BrandSize
import com.knowroaming.esim.app.util.AppRoute
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun ManualInstallationScreen(navigator: Navigator, state: ESimOrderState) {

    val clipboard = LocalClipboardManager.current

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
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth().padding(BrandSize.lg),
                    verticalArrangement = Arrangement.spacedBy(BrandSize.lg),
                ) {
                    Text(
                        text = "Manual Installation",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight(700)
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(BrandSize.md),
                    )


                    Text(
                        text = "To ensure a smooth setup process, we recommend installing your eSIM just before your departure, as installation requires a stable Internet connection. However, if you prefer, you can wait until you arrive in the country to activate your eSIM using WIFI. Once your eSIM is installed, you have the option to turn it off. Activation of the package only occurs when you use your eSIM in the country / region where it is eligible for connection."
                    )

                    Text(
                        modifier = Modifier.align(Alignment.Start),
                        text = "Configure APNs on your device:",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight(700)
                        ),
                    )

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "1.",
                            )

                            Spacer(modifier = Modifier.size(BrandSize.md))

                            Text(
                                text = "Settings",
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "2.",
                            )

                            Spacer(modifier = Modifier.size(BrandSize.md))

                            Text(
                                text = "Mobile Data",
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "3.",
                            )

                            Spacer(modifier = Modifier.size(BrandSize.md))

                            Text(
                                text = "Select eSIM > Mobile Data Network",
                            )
                        }
                        Row {
                            Text(
                                text = "4.",
                            )

                            Spacer(modifier = Modifier.size(BrandSize.md))

                            Text(
                                text = "Change APN to 'globaldata' (lower case, no spaces).",
                            )
                        }

                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.height(BrandSize.x3l).clickable {
                            state.order?.smDpAddress?.let { clipboard.setText(AnnotatedString(it)) }
                        },
                        horizontalArrangement = Arrangement.spacedBy(BrandSize.lg),
                    ) {
                        Text(
                            text = "SM-DP+ Address:", modifier = Modifier.weight(1f)
                        )

                        Row(
                            modifier = Modifier.weight(1f).clip(RoundedCornerShape(BrandSize.sm))
                        ) {
                            Box(
                                modifier = Modifier.weight(1f).height(BrandSize.x3l)
                                    .background(BrandColor.Gray200),
                            ) {
                                Text(
                                    overflow = TextOverflow.Ellipsis,
                                    text = state.order?.smDpAddress ?: "",
                                    modifier = Modifier.align(Alignment.Center)
                                        .padding(BrandSize.sm)
                                )
                            }

                            Icon(
                                contentDescription = "Copy",
                                modifier = Modifier.size(BrandSize.x3l)
                                    .background(MaterialTheme.colorScheme.primary)
                                    .padding(BrandSize.md),
                                imageVector = Icons.Outlined.ContentCopy,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.height(BrandSize.x3l).clickable {
                            state.order?.activationCode?.let { clipboard.setText(AnnotatedString(it)) }
                        },
                        horizontalArrangement = Arrangement.spacedBy(BrandSize.lg),
                    ) {

                        Text(
                            text = "Activation Code:", modifier = Modifier.weight(1f)
                        )

                        Row(
                            modifier = Modifier.weight(1f).clip(RoundedCornerShape(BrandSize.sm))
                        ) {
                            Box(
                                modifier = Modifier.weight(1f).height(BrandSize.x3l)
                                    .background(BrandColor.Gray200),
                            ) {
                                Text(
                                    overflow = TextOverflow.Ellipsis,
                                    text = state.order?.activationCode ?: "",
                                    modifier = Modifier.align(Alignment.Center)
                                        .padding(BrandSize.sm)
                                )
                            }

                            Icon(
                                contentDescription = "Copy",
                                modifier = Modifier.size(BrandSize.x3l)
                                    .background(MaterialTheme.colorScheme.primary)
                                    .padding(BrandSize.md),
                                imageVector = Icons.Outlined.ContentCopy,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }


                    AppButton(text = "My eSIMs", onClick = {
                        navigator.navigate(AppRoute.MY_ESIMS)
                    })
                }
            }
        }
    }
}