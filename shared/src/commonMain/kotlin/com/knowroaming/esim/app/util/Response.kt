package com.knowroaming.esim.app.util

sealed interface Response<out T>  {
    data class Success<T>(val data: T) : Response<T>
    data class Error(val message: String? = null) : Response<Nothing>
    data object Loading : Response<Nothing>
}
