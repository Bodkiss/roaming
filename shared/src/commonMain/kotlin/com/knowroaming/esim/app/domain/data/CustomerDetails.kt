package com.knowroaming.esim.app.domain.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CustomerDetails(
    @SerialName("email")
    val email: String? = null,
    @SerialName("first_name")
    val firstName: String? = null,
    @SerialName("last_name")
    val lastName: String? = null,
    @SerialName("phone_number")
    val phoneNumber: String? = null
)