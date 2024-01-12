package com.knowroaming.esim.app.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.component.setupDefaultComponents
import com.seiko.imageloader.defaultImageResultMemoryCache
import kotlinx.cinterop.BetaInteropApi
import okio.Path.Companion.toPath
import org.jetbrains.skia.Image
import platform.Foundation.NSCachesDirectory
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@Composable
actual fun generateImageLoader(): ImageLoader {
    return remember {
        ImageLoader {
            components {
                setupDefaultComponents()
            }
            interceptor {
                // cache 100 success image result, without bitmap
                defaultImageResultMemoryCache()
                memoryCacheConfig {
                    maxSizeBytes(32 * 1024 * 1024) // 32MB
                }

                diskCacheConfig {
                    directory(getCacheDir().toPath().resolve("image_cache"))
                    maxSizeBytes(512L * 1024 * 1024) // 512MB
                }
            }
        }
    }
}

private fun getCacheDir(): String {
    return NSSearchPathForDirectoriesInDomains(
        NSCachesDirectory,
        NSUserDomainMask,
        true,
    ).first() as String
}

@OptIn(ExperimentalEncodingApi::class, BetaInteropApi::class)
actual fun decodeBase64ToBitmap(base64String: String): ImageBitmap {
    val bytes = Base64.decode(base64String)
    return Image.makeFromEncoded(bytes).toComposeImageBitmap()
}