package com.knowroaming.esim.app.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import com.seiko.imageloader.ImageLoader

@Composable
expect fun generateImageLoader(): ImageLoader

expect fun decodeBase64ToBitmap(base64String: String): ImageBitmap
