package com.knowroaming.esim.app.domain.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CustomerForgetPassword(
    @SerialName("customer_id")
    val id: String,
    val email: String? = null,
)