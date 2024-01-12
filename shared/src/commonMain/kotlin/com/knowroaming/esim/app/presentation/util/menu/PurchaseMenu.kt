package com.knowroaming.esim.app.presentation.util.menu

import com.knowroaming.esim.app.util.AppRoute
import moe.tlaster.precompose.navigation.BackStackEntry
import moe.tlaster.precompose.navigation.query

data class PurchaseMenu(private val orderId: String? = null) : AppMenu {
    override val items: List<AppMenu.Item>
        get() = listOf(
            AppMenu.Item(
                label = "Auto", route = "${AppRoute.DESTINATION_PRODUCT_PURCHASED}?method=auto"
            ),
            AppMenu.Item(
                label = "QR Code",
                route = "${AppRoute.DESTINATION_PRODUCT_PURCHASED}?method=qr_code"
            ),
            AppMenu.Item(
                label = "Manual", route = "${AppRoute.DESTINATION_PRODUCT_PURCHASED}?method=manual"
            ),
        ).map { item ->
            item.copy(route = orderId?.let { item.route + "&order_uuid=$orderId" } ?: item.route)
        }

    override fun selected(backstack: BackStackEntry, item: AppMenu.Item): Boolean {
        return item.route.contains(backstack.query("method", "auto") ?: "auto")
    }
}