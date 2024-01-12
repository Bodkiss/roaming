package com.knowroaming.esim.app.domain.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class CustomerEdit (
    @SerialName("customer_id")
    val id: String? = null,
    val email: String,
    val password: String,
    @SerialName("first_name")
    val firstName: String,
    @SerialName("last_name")
    val lastName: String? = null,
    @SerialName("full_name")
    val fullName: String? = null,
    @SerialName("phone_number")
    val phoneNumber: String? = null,
    @SerialName("new_customer_id")
    val newId: String? = null,
    ) {
        @Serializable
        data class Response(
            @SerialName("customer_id")
            val id: String,
            val email: String,
            val updated: Boolean? = null,
        )
    }