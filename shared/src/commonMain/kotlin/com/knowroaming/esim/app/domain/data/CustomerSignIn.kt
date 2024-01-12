package com.knowroaming.esim.app.domain.data

import com.knowroaming.esim.app.domain.model.Customer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CustomerSignIn(
    val email: String,
    val password: String,
) {
    @Serializable
    data class Response(
        @SerialName("get_session_auth_hash")
        val session: String? = null,
        @SerialName("customer_details")
        val customer: Customer? = null,
        @SerialName("customer_authenticated")
        val authenticated: Boolean = false,
    )
}

typealias AuthData = CustomerSignIn.Response