package com.knowroaming.esim.app.domain.data

import com.knowroaming.esim.app.domain.model.Customer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CustomerResetPassword(
    @SerialName("customer_id")
    val id: String,
    @SerialName("password_reset_token")
    val token: String? = null,
    val password: String? = null,
    @SerialName("new_password")
    val confirmation: String,
){

    @Serializable
    data class Response(
        @SerialName("customer_details")
        val customer: Customer,
        val success: Boolean,
    )
}