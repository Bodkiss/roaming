package com.knowroaming.esim.app.domain.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SupportedCountry(
    @SerialName("country_name")
    val name: String,
    @SerialName("country_code")
    val code: String,
)