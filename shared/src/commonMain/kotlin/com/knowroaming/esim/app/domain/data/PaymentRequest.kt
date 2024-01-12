package com.knowroaming.esim.app.domain.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaymentRequest(
    @SerialName("type") val type: String,
    @SerialName("iccid") val iccid: String? = null,
    @SerialName("customer") val customer: CustomerDetails,
    @SerialName("package_type_id") val packageTypeId: Int,
    @SerialName("payment_method") val paymentMethod: String,
) {

    object Type {
        const val BUY = "buy"
        const val TOP_UP = "top-up"
    }

    object Method {
        const val STRIPE_INTENT = "stripe_intent"
        const val STRIPE_CHECKOUT = "stripe_checkout"
    }

    @Serializable
    data class Response(
        /** link to payment gateway or the intent secret for stripe */
        @SerialName("uri") val uri: String,
        @SerialName("order_id") val orderId: String,
        @SerialName("customer_ref") val customerRef: String? = null,
        @SerialName("ephemeral_key") val ephemeralKey: String? = null,
        @SerialName("publishable_key") val publishableKey: String? = null,
        @SerialName("is_intent") val isIntent: Boolean = false,
    )
}