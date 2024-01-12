package com.knowroaming.esim.app.presentation.util.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import moe.tlaster.precompose.navigation.Navigator

private val InitialState = SearchState(
    query = "", active = false, placeholder = "Search for a topic"
)
class TopicSearch  : AppSearch {
    private val _state = MutableStateFlow(
        InitialState   )

    @OptIn(ExperimentalCoroutinesApi::class)
    override val state: SearchState
        @Composable get() = _state.mapLatest {
            it.copy(
                active = it.active || it.query.isNotEmpty()
            )
        }.collectAsState(InitialState).value

    override fun onSearch(value: String) {
    }

    override fun onQueryChange(value: String) {
        _state.update {
            it.copy(query = value)
        }
    }

    override fun onActiveChange(value: Boolean) {
        _state.update {
            it.copy(active = value)
        }
    }

    @Composable
    override fun Content(navigator: Navigator): LazyListScope.() -> Unit {


        return {
            item {

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

    }
}
