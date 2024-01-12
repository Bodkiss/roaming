package com.knowroaming.esim.app.presentation.util.search

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import moe.tlaster.precompose.navigation.Navigator


data class SearchState(
    val query: String,
    val active: Boolean,
    val placeholder: String,
)

interface AppSearch {
    @get:Composable
    val state: SearchState
    fun onSearch(value: String)
    fun onQueryChange(value: String)
    fun onActiveChange(value: Boolean)

    @Composable
    fun Content(navigator: Navigator): LazyListScope.() -> Unit
}