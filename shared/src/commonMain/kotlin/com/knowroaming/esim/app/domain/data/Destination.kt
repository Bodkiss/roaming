package com.knowroaming.esim.app.domain.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Destination(
    @SerialName("country_code")
    val code: String? = null,
    @SerialName("country_name")
    val name: String? = null,
    val region: String? = null,
)