package com.knowroaming.esim.app.presentation.view.extra

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.knowroaming.esim.app.presentation.components.button.AppButton
import com.knowroaming.esim.app.presentation.components.button.AppButtonVariant
import com.knowroaming.esim.app.presentation.components.main.AppDivider
import com.knowroaming.esim.app.presentation.components.main.AppTextButton
import com.knowroaming.esim.app.presentation.theme.BrandSize
import com.knowroaming.esim.app.util.AppRoute
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource


@OptIn(ExperimentalResourceApi::class)
@Composable
fun AboutScreen(navigator: Navigator) {
    val uriHandler = LocalUriHandler.current

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(BrandSize.lg, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Image(
                painter = painterResource("icon.png"),
                contentDescription = "Logo",
                modifier = Modifier.padding(BrandSize.md).fillMaxWidth().height(128.dp),
            )
        }

        item {
            AppButton(
                text = "Terms & Conditions",
                type = AppButtonVariant.Primary,
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = BrandSize.lg, vertical = BrandSize.md),
                shape = MaterialTheme.shapes.extraSmall,
            ) {
                navigator.navigate(AppRoute.TERMS_AND_CONDs)
            }
        }

        item {
            AppButton(
                text = "Privacy Policy",
                type = AppButtonVariant.Primary,
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = BrandSize.lg, vertical = BrandSize.md),
                shape = MaterialTheme.shapes.extraSmall,
            ) {
                navigator.navigate(AppRoute.PRIVACY_POLICY)
            }
        }

        item {
            AppDivider()
        }

        item {
            AppTextButton(
                text = "knowroaming.com"
            ) {
                uriHandler.openUri("https://knowroaming.com/")
            }
        }

        item {
            Text(
                text = "Copyright 2023"
            )
        }
    }
}