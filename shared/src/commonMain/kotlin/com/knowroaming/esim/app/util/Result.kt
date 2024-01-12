package com.knowroaming.esim.app.util

import de.jensklingenberg.ktorfit.converter.Converter
import de.jensklingenberg.ktorfit.converter.KtorfitResult
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject

sealed interface ApiResult<out T> {

    @Serializable
    data class Error(val detail: String? = null) : ApiResult<Nothing>

    @Serializable
    data class Success<T>(val detail: String? = null, val data: T) : ApiResult<T>

    companion object {
        inline fun <reified T> converterFactoryOf(): Converter.SuspendResponseConverter<HttpResponse, *> {
            return object : Converter.SuspendResponseConverter<HttpResponse, Any> {
                @Suppress("OVERRIDE_DEPRECATION")
                override suspend fun convert(response: HttpResponse): Any {
                    return convert(KtorfitResult.Success(response))
                }

                override suspend fun convert(result: KtorfitResult): Any {
                    return when (result) {
                        is KtorfitResult.Success -> {
                            when (result.response.status.value) {
                                400, 401, 403 -> result.response.body<Error>()
                                else -> {
                                    val json = result.response.body<String>()

                                    val element = Json.parseToJsonElement(json)

                                    return if (element.jsonObject.containsKey("detail")) {
                                        Json.decodeFromString<Success<T>>(json)
                                    } else {
                                        Success(data = Json.decodeFromString(json))
                                    }
                                }
                            }
                        }

                        is KtorfitResult.Failure -> throw result.throwable
                    }
                }
            }
        }
    }
}