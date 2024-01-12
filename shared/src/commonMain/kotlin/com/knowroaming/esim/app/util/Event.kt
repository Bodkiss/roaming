package com.knowroaming.esim.app.util

sealed interface Event<out T> {
    data object Loading : Event<Nothing>
    data object Completed : Event<Nothing>
    data class Failure(val message: String? = null) : Event<Nothing>
    data class Notification<T>(val message: String? = null, val data: T? = null) : Event<T>
}