package com.knowroaming.esim.app.presentation.components.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.knowroaming.esim.app.presentation.theme.AppTheme
import com.knowroaming.esim.app.presentation.theme.BrandSize
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import io.ktor.websocket.Frame

@Composable
fun AppWebViewScreen(url: String) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = BrandSize.md,
        contentColor = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.fillMaxWidth().padding(vertical = BrandSize.lg)
    ) {
        AppWebView(url)
    }
}

@Composable
internal fun AppWebView(url: String) {
    AppTheme {
        val webViewState = rememberWebViewState(url)
        Column(Modifier.fillMaxSize()) {
            val text = webViewState.let {
                "${it.pageTitle ?: ""} ${it.loadingState} ${it.lastLoadedUrl ?: ""}"
            }
            Frame.Text(text)
            WebView(
                state = webViewState, modifier = Modifier.fillMaxSize()
            )
        }
    }
}