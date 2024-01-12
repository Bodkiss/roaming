package com.knowroaming.esim.app.presentation.components.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.knowroaming.esim.app.presentation.components.button.AppIconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSearchBar(
    query: String,
    active: Boolean,
    modifier: Modifier = Modifier,
    placeholder: String = "Search for results",
    onSearch: (String) -> Unit,
    onQueryChange: (String) -> Unit,
    onActiveChange: (Boolean) -> Unit,
    content: @Composable (ColumnScope.() -> Unit)
) {
    SearchBar(content = content,
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
            dividerColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
            inputFieldColors = TextFieldDefaults.colors(
                cursorColor = MaterialTheme.colorScheme.onBackground,
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                focusedContainerColor = MaterialTheme.colorScheme.onBackground,
                focusedIndicatorColor = MaterialTheme.colorScheme.onBackground,
                focusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                unfocusedContainerColor = MaterialTheme.colorScheme.onBackground,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
                disabledIndicatorColor = MaterialTheme.colorScheme.onBackground,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground,
                errorTextColor = MaterialTheme.colorScheme.onBackground,
                errorIndicatorColor = MaterialTheme.colorScheme.onBackground,
                errorPlaceholderColor = MaterialTheme.colorScheme.onBackground,
                disabledTextColor = MaterialTheme.colorScheme.onBackground,
                disabledContainerColor = MaterialTheme.colorScheme.onBackground,
                disabledPlaceholderColor = MaterialTheme.colorScheme.onBackground,
            )
        ),
        shape = MaterialTheme.shapes.extraLarge,
        modifier = modifier.padding(
            if (active) PaddingValues(0.dp) else PaddingValues(16.dp)
        ).fillMaxWidth(),
        query = query,
        active = active,
        onSearch = onSearch,
        onQueryChange = onQueryChange,
        onActiveChange = onActiveChange,
        tonalElevation = 12.dp,
        placeholder = {
            Text(
                text = placeholder,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 16.sp,
                    lineHeight = 19.2.sp,
                    fontWeight = FontWeight(400),
                ),
            )
        },
        leadingIcon = {
            AppIconButton(
                backgroundColor = Color.Transparent,
                color = MaterialTheme.colorScheme.onSurface.copy(
                    alpha = if (active) 1f else 0.6f
                ),
                icon = if (active) Icons.Default.ArrowBack else Icons.Default.Search,
                description = if (active) "Go Back" else "Open Search"
            ) {
                onQueryChange("")
                onActiveChange(!active)
            }
        },
        trailingIcon = {
            AnimatedVisibility(active && query.isNotEmpty()) {
                AppIconButton(
                    icon = Icons.Default.Clear, description = "Clear Search"
                ) {
                    onQueryChange("")
                }
            }
        })
}