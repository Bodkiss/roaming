package com.knowroaming.esim.app.presentation.util.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.knowroaming.esim.app.presentation.components.country.DestinationCard
import com.knowroaming.esim.app.presentation.model.CountryListViewModel
import com.knowroaming.esim.app.util.AppRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.navigation.Navigator


private val InitialState = SearchState(
    query = "", active = false, placeholder = "Search for a country"
)

class DestinationSearch(private val viewModel: CountryListViewModel) : AppSearch {
    private val _state = MutableStateFlow(
        InitialState
    )

    private val search = MutableStateFlow<Job?>(null)

    private fun search(value: String, timeout: Long = 0) {
        CoroutineScope(Dispatchers.IO).launch {
            if (search.value?.isActive == true) {
                search.value?.cancelAndJoin()
            }

            delay(timeout)

            search.update { viewModel.search(value) }
        }
    }


    override val state: SearchState
        @Composable get() = _state.map {
            it.copy(
                active = it.active || it.query.isNotEmpty()
            )
        }.collectAsState(InitialState).value

    override fun onSearch(value: String) {
        search(value)
    }

    override fun onQueryChange(value: String) {
        _state.update {
            it.copy(query = value)
        }

        search(value, 600)
    }

    override fun onActiveChange(value: Boolean) {
        _state.update {
            it.copy(active = value)
        }
    }

    @Composable
    override fun Content(navigator: Navigator): LazyListScope.() -> Unit {
        val state by viewModel.search

        return {
            item {
                AnimatedVisibility(
                    state.loading, enter = fadeIn(), exit = fadeOut()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "Loading Results...",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontSize = 16.sp,
                                lineHeight = 19.2.sp,
                                fontWeight = FontWeight(400),
                            ),
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }

            items(state.data.filter { _state.value.query.isNotEmpty() },
                key = { it.code }) { country ->
                DestinationCard(country) {
                    _state.update { InitialState }

                    if (AppRoute.DESTINATIONS_GLOBAL.contains(country.code)) {
                        navigator.navigate(AppRoute.DESTINATIONS_GLOBAL)
                    } else {
                        navigator.navigate("/store/destinations/${country.code}?is_region=${country.isRegion}")
                    }
                }
            }
        }
    }
}