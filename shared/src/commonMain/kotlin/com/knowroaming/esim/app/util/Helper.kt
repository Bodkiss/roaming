package com.knowroaming.esim.app.util

import androidx.compose.runtime.Composable

@Composable
expect fun OpenEmailClient(recipient: String, subject: String, body: String)

@Composable
expect fun OpenWhatsApp(message: String)
