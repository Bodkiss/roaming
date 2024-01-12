package com.knowroaming.esim.app.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderDetail(
    @SerialName("activation_code")
    val activationCode: String,
    @SerialName("country")
    val country: Country,
    @SerialName("country_code")
    val countryCode: String,
    @SerialName("country_name")
    val countryName: String,
    @SerialName("customer_id")
    val customerId: String,
    @SerialName("discount_amount")
    val discountAmount: Double,
    @SerialName("discount_code")
    val discountCode: String,
    @SerialName("final_price")
    val finalPrice: Double,
    @SerialName("iccid")
    val iccid: String,
    @SerialName("order_date")
    val orderDate: String,
    @SerialName("order_number")
    val orderNumber: Int,
    @SerialName("order_status")
    val orderStatus: String,
    @SerialName("order_type")
    val orderType: String,
    @SerialName("package_data_size")
    val packageDataSize: Double,
    @SerialName("package_name")
    val packageName: String,
    @SerialName("package_validity")
    val packageValidity: Int,
    @SerialName("password_reset_encoded")
    val passwordResetEncoded: String? = null,
    @SerialName("purchase_currency")
    val purchaseCurrency: String,
    @SerialName("purchase_price")
    val purchasePrice: Double,
    @SerialName("qr_code")
    val qrCode: String,
    @SerialName("qr_code_image_base64")
    val qrCodeImageBase64: String,
    @SerialName("sm_dp_address")
    val smDpAddress: String,
    @SerialName("transaction_id")
    val transactionId: String
)