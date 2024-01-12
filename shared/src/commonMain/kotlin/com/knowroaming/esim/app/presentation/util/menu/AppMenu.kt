package com.knowroaming.esim.app.presentation.util.menu

import androidx.compose.runtime.Composable
import moe.tlaster.precompose.navigation.BackStackEntry


interface AppMenu {

    val items: List<Item>

    fun selected(backstack: BackStackEntry, item: Item): Boolean

    data class Item(
        val label: String, val route: String, val onClick: @Composable (() -> Unit)? = null
    )
}