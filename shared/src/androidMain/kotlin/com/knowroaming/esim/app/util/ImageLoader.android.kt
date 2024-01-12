package com.knowroaming.esim.app.util

import android.graphics.BitmapFactory
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.cache.memory.maxSizePercent
import com.seiko.imageloader.component.setupDefaultComponents
import com.seiko.imageloader.defaultImageResultMemoryCache
import com.seiko.imageloader.option.androidContext
import okio.Path.Companion.toOkioPath
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@Composable
actual fun generateImageLoader(): ImageLoader {
    val activity = LocalContext.current as ComponentActivity

    return remember(activity) {
        ImageLoader {
            options {
                androidContext(activity)
            }
            components {
                setupDefaultComponents()
            }
            interceptor {
                // cache 100 success image result, without bitmap
                defaultImageResultMemoryCache()
                memoryCacheConfig {
                    // Set the max size to 25% of the app's available memory.
                    maxSizePercent(activity, 0.25)
                }
                diskCacheConfig {
                    directory(activity.cacheDir.resolve("image_cache").toOkioPath())
                    maxSizeBytes(512L * 1024 * 1024) // 512MB
                }
            }
        }
    }
}

@OptIn(ExperimentalEncodingApi::class)
actual fun decodeBase64ToBitmap(base64String: String): ImageBitmap {
    val bytes = Base64.decode(base64String)
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size).asImageBitmap()
}