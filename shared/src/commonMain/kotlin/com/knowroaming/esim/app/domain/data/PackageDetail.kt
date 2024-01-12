package com.knowroaming.esim.app.domain.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PackageDetail(
    @SerialName("date_created_epoch")
    val dateCreatedEpoch: Long,
    @SerialName("date_created_utc")
    val dateCreatedUtc: String,
    @SerialName("window_activation_start_utc")
    val windowActivationStartUtc: String,
    @SerialName("window_activation_end_utc")
    val windowActivationEndUtc: String,
    val status: String,
    @SerialName("voice_usage_remaining_seconds")
    val voiceUsageRemainingSeconds: Long,
    @SerialName("data_usage_remaining_gigabytes")
    val dataUsageRemainingGigabytes: Double,
    @SerialName("data_usage_remaining_bytes")
    val dataUsageRemainingBytes: Double,
    @SerialName("sms_usage_remaining_nums")
    val smsUsageRemainingNums: Int,
    @SerialName("window_activation_start_epoch")
    val windowActivationStartEpoch: Long,
    @SerialName("window_activation_end_epoch")
    val windowActivationEndEpoch: Long,
    @SerialName("time_allowance_seconds")
    val timeAllowanceSeconds: Double,
    @SerialName("time_allowance_days")
    val timeAllowanceDays: Double,
    @SerialName("data_allowance_gigabytes")
    val dataAllowanceGigabytes: Double,
    @SerialName("data_allowance_bytes")
    val dataAllowanceBytes: Double,
    @SerialName("package_country_name")
    val packageCountryName: String
)