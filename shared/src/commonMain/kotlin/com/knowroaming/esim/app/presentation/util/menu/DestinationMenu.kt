package com.knowroaming.esim.app.presentation.util.menu

import com.knowroaming.esim.app.util.AppRoute
import moe.tlaster.precompose.navigation.BackStackEntry
import moe.tlaster.precompose.navigation.query

object DestinationMenu : AppMenu {
    override val items: List<AppMenu.Item>
        get() = listOf(
            AppMenu.Item(
                label = "Countries", route = AppRoute.DESTINATIONS_All
            ),
            AppMenu.Item(
                label = "Regions", route = AppRoute.DESTINATIONS_REGIONS
            ),
            AppMenu.Item(
                label = "Global", route = AppRoute.DESTINATIONS_GLOBAL
            ),
        )

    override fun selected(backstack: BackStackEntry, item: AppMenu.Item): Boolean {

        val isGlobal = backstack.path == AppRoute.DESTINATIONS_GLOBAL

        val isRegion = backstack.route.route == AppRoute.DESTINATION && backstack.query(
            "is_region", false
        ) ?: false

        val isCountry = !isGlobal && !isRegion

        return when (item.route) {
            AppRoute.DESTINATIONS_GLOBAL -> isGlobal

            AppRoute.DESTINATIONS_REGIONS -> isRegion || backstack.route.route == AppRoute.DESTINATIONS_REGIONS

            AppRoute.DESTINATIONS_All -> isCountry && backstack.route.route in listOf(
                AppRoute.DESTINATION, AppRoute.DESTINATIONS, AppRoute.DESTINATIONS_All
            )

            else -> backstack.hasRoute(item.route)
        }
    }
}