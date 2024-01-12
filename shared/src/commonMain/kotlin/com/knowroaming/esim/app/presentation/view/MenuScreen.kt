package com.knowroaming.esim.app.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.knowroaming.esim.app.presentation.components.button.AppButton
import com.knowroaming.esim.app.presentation.components.button.AppButtonVariant
import com.knowroaming.esim.app.presentation.theme.BrandSize
import com.knowroaming.esim.app.presentation.util.menu.AppMenu
import com.knowroaming.esim.app.util.isMainTabRoute
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.PopUpTo
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun MenuScreen(navigator: Navigator, menu: AppMenu) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        item {
            Image(
                painter = painterResource("icon.png"),
                contentDescription = "Logo",
                modifier = Modifier.padding(BrandSize.md).fillMaxWidth().height(128.dp),
            )
        }

        item { Spacer(modifier = Modifier) }

        item { Spacer(modifier = Modifier.size(BrandSize.x2l)) }

        items(menu.items) { item ->
            val (invoke, setInvoke) = remember { mutableStateOf(false) }

            val current by navigator.currentEntry.collectAsState(null)

            val selected = current?.let { menu.selected(it, item) } ?: false

            if (invoke) {
                item.onClick?.invoke()
            }

            MenuButton(item.label, selected, onClick = {
                if (item.onClick !== null) {
                    setInvoke(true)
                } else {
                    navigator.navigate(
                        item.route, options = NavOptions(
                            popUpTo = PopUpTo.First(inclusive = item.route.isMainTabRoute())
                        )
                    )
                }
            })
        }

        item { Spacer(modifier = Modifier) }
    }
}

@Composable
fun MenuButton(name: String, active: Boolean = false, onClick: () -> Unit = {}) {

    val type = if (active) AppButtonVariant.Secondary else AppButtonVariant.Tertiary
    AppButton(
        text = name,
        type = type,
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(0.6f)
            .padding(horizontal = BrandSize.lg, vertical = BrandSize.md),
        shape = MaterialTheme.shapes.extraLarge,
    )
}

