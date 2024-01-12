package com.knowroaming.esim.app.presentation.components.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.knowroaming.esim.app.presentation.components.button.AppButton
import com.knowroaming.esim.app.presentation.components.button.AppButtonVariant
import com.knowroaming.esim.app.presentation.components.button.AppIconButton
import com.knowroaming.esim.app.presentation.theme.BrandColor
import com.knowroaming.esim.app.presentation.theme.BrandGradient
import com.knowroaming.esim.app.presentation.theme.BrandSize
import com.knowroaming.esim.app.presentation.util.menu.AppMenu
import com.knowroaming.esim.app.presentation.util.search.AppSearch
import com.knowroaming.esim.app.util.AppRoute
import com.knowroaming.esim.app.util.isActive
import com.knowroaming.esim.app.util.isMainTabRoute
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.mapLatest
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.PopUpTo
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigationTopBar(
    title: String? = null, navigator: Navigator
) {
    val current by navigator.currentEntry.collectAsState(null)

    val isMainTabRoute = current?.isMainTabRoute() ?: false

    val isMenuOpen = current?.hasRoute(AppRoute.MENU) ?: false

    val containerColor = when (true) {
        isMainTabRoute -> MaterialTheme.colorScheme.surface
        current?.hasRoute(AppRoute.FAQ) -> MaterialTheme.colorScheme.surface
        else -> MaterialTheme.colorScheme.background
    }

    CenterAlignedTopAppBar(
        title = {
            AppNavigationTitle(title)
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor,
        ),
        navigationIcon = {
            AppNavigationArrowBack(navigator)
        },
        actions = {
            if (isMenuOpen) {
                AppNavigationCloseMenu(navigator)
            } else {
                AppNavigationOpenMenu(navigator)
            }
        },
    )
}


@Composable
fun AppNavigationBar(
    title: String,
    navigator: Navigator,
    menu: AppMenu? = null,
    search: AppSearch? = null,
) {
    Column(
        modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface)
    ) {

        AnimatedVisibility(search?.state?.active != true) {
            AppNavigationTopBar(title = title, navigator = navigator)
        }

        if (search != null) {

            val searchContent = search.Content(navigator)

            AppSearchBar(
                query = search.state.query,
                active = search.state.active,
                onSearch = { search.onSearch(it) },
                placeholder = search.state.placeholder,
                onQueryChange = { search.onQueryChange(it) },
                onActiveChange = { search.onActiveChange(it) },
            ) {
                LazyColumn(
                    modifier = Modifier.padding(BrandSize.lg).fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(BrandSize.lg)
                ) {
                    searchContent()
                }
            }
        }

        if (menu?.items?.isNotEmpty() == true) {
            Row(
                modifier = Modifier.padding(BrandSize.lg),
                horizontalArrangement = Arrangement.spacedBy(BrandSize.md),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                menu.items.forEach { item ->
                    val current by navigator.currentEntry.collectAsState(null)

                    val selected = current?.let { menu.selected(it, item) } ?: false

                    AppButton(
                        text = item.label,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 16.sp,
                            lineHeight = 19.2.sp,
                            fontWeight = FontWeight(400),
                            textAlign = TextAlign.Center,
                        ),
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        type = if (selected) AppButtonVariant.Secondary else AppButtonVariant.Tertiary
                    ) {
                        navigator.navigate(
                            item.route, options = NavOptions(
                                popUpTo = PopUpTo.First(false)
                            )
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AppNavigationTitle(
    title: String? = null,
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource("icon.png"),
            contentDescription = "App Icon",
            modifier = Modifier.size(48.dp)
        )
        if (title != null) {
            Text(
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(

                    fontWeight = FontWeight(500)
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}


@Composable
fun AppNavigationOpenMenu(navigator: Navigator) {
    val current by navigator.currentEntry.collectAsState(null)

    Row(
        modifier = Modifier.padding(BrandSize.xl),
    ) {
        AppIconButton(
            icon = Icons.Default.Menu,
            description = "Open Menu",
            backgroundColor = MaterialTheme.colorScheme.surface,
        ) {
            current?.route?.let { route ->
                navigator.navigate(
                    "${AppRoute.MENU}?active=${route.route}"
                )
            }
        }
    }
}

@Composable
fun AppNavigationCloseMenu(navigator: Navigator) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(BrandSize.xl),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AppIconButton(
            backgroundColor = MaterialTheme.colorScheme.surface,
            icon = Icons.Default.Close,
            description = "Close Menu"
        ) {
            navigator.goBack()
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun AppNavigationArrowBack(navigator: Navigator) {

    val canGoBack by navigator.canGoBack.collectAsState(false)

    val menuIsOpen by navigator.currentEntry.mapLatest { it?.hasRoute(AppRoute.MENU) == true }
        .collectAsState(false)

    if (canGoBack && !menuIsOpen) {
        Row(
            modifier = Modifier.padding(BrandSize.xl),
        ) {
            AppIconButton(
                icon = Icons.Default.ArrowBack,
                description = "Go Back",
                backgroundColor = BrandColor.Gray400,
            ) {
                navigator.goBack()
            }
        }
    }
}


@Composable
fun AppBottomNavigation(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier.fillMaxWidth().background(Color.Transparent).padding(
            top = BrandSize.lg, start = BrandSize.lg, end = BrandSize.lg
        ).height(BrandSize.x6l).selectableGroup(),
        horizontalArrangement = Arrangement.spacedBy(BrandSize.lg),
        content = content
    )
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun AppBottomNavigationBar(navigator: Navigator) {
    val isMainTabRoute by navigator.currentEntry.mapLatest { current ->
        current?.isMainTabRoute() == true
    }.collectAsState(false)

    if (isMainTabRoute) {
        AppBottomNavigation(
            backgroundColor = Color.Transparent,
            modifier = Modifier.background(brush = BrandGradient.Background),
        ) {
            TabNavigationItem(
                title = "Store", AppRoute.STORE, navigator
            )
            TabNavigationItem(
                title = "My eSims", AppRoute.MY_ESIMS, navigator
            )
            TabNavigationItem(
                title = "Profile", AppRoute.PROFILE, navigator
            )
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
private fun RowScope.TabNavigationItem(
    title: String, route: String, navigator: Navigator
) {
    val isActive by navigator.currentEntry.mapLatest { current ->
        current?.isActive(route) == true
    }.collectAsState(false)


    AppButton(
        text = title,
        elevation = BrandSize.x5l,
        type = if (isActive) AppButtonVariant.Active else AppButtonVariant.Muted,
        modifier = Modifier.weight(1f)
    ) {
        navigator.navigate(
            route, options = NavOptions(
                launchSingleTop = true, popUpTo = PopUpTo.First()
            )
        )
    }
}