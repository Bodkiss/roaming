package com.knowroaming.esim.app.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Country(
    @SerialName("country_name")
    val name: String,
    @SerialName("country_code")
    val code: String,
    @SerialName("country_flag")
    val flag: String,
    @SerialName("country_flag_css")
    val flagCss: String,
    @SerialName("country_name_slug")
    val slug: String,
    @SerialName("is_region")
    val isRegion: Boolean = false,
    @SerialName("from_price")
    val fromPrice: Float? = null,
)
