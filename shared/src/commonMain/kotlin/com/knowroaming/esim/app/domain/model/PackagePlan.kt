package com.knowroaming.esim.app.domain.model

import com.knowroaming.esim.app.domain.data.SupportedCountry
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PackagePlan(
    val name: String,
    val price: Double,
    @SerialName("data_GB")
    val data: Double,
    val country: Country,
    val network: List<String> = emptyList(),
    val currency: String,
    @SerialName("plan_type")
    val planType: String,
    @SerialName("kyc_display")
    val kycDisplay: String,
    @SerialName("package_slug")
    val packageSlug: String,
    @SerialName("validity_days")
    val validityDays: Long,
    @SerialName("package_type_id")
    val packageTypeId: Long,
    @SerialName("best_connectivity")
    val bestConnectivity: String,
    @SerialName("activation_policy")
    val activationPolicy: String,
    @SerialName("supported_countries")
    val supportedCountries: List<SupportedCountry>,
    @SerialName("name_additional_text")
    val nameAdditionalText: String,
)