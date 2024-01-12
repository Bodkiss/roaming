package com.knowroaming.esim.app.util.converter

import de.jensklingenberg.ktorfit.converter.Converter
import de.jensklingenberg.ktorfit.converter.KtorfitResult
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val total: Int, val count: Int, val offset: Int, val elements: List<T>
) {
    companion object {
        inline fun <reified T> converterFactoryOf(): Converter.SuspendResponseConverter<HttpResponse, *> {
            return object : Converter.SuspendResponseConverter<HttpResponse, Any> {
                @Suppress("OVERRIDE_DEPRECATION")
                override suspend fun convert(response: HttpResponse): Any {
                    return convert(KtorfitResult.Success(response))
                }

                override suspend fun convert(result: KtorfitResult): Any {
                    return when (result) {
                        is KtorfitResult.Success -> result.response.body<BaseResponse<T>>().elements
                        is KtorfitResult.Failure -> throw result.throwable
                    }
                }
            }
        }
    }
}