package com.knowroaming.esim.app.domain.data

import com.knowroaming.esim.app.domain.model.Customer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CustomerRegister(
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
    @SerialName("referred_by")
    val referredBy: String? = null,
    @SerialName("new_customer_id")
    val newId: String? = null,
) {
    @Serializable
    data class Response(
        @SerialName("customer_id")
        val id: String,
        val email: String,
        val message: String? = null,
        val success: Boolean? = null,
        val referral: String? = null,
        @SerialName("referral_code")
        val referralCode: String? = null,
        val updated: Boolean? = null,
    )
}

fun CustomerRegister.Response.toCustomer(phoneNumber: String?, firstName: String, lastName: String): Customer {
    return Customer(
        id = this.id,
        email = this.email,
        phoneNumber = phoneNumber,
        firstName = firstName,
        lastName = lastName,
        fullName = "$firstName $lastName",

        )
}