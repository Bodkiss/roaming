package com.knowroaming.esim.app.domain.data

import com.knowroaming.esim.app.domain.model.Country
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EsimRequest(
    @SerialName("customer_id") val customerId: String,
    @SerialName("show_package_details") val getPackageDetails: Boolean,
    @SerialName("show_balance_remaining") val getBalanceRemaining: Boolean
) {
    @Serializable
    data class Response(
        val iccid: String,
        val country: Country,
        @SerialName("assigned_date") val assignedDate: String,
        @SerialName("package_details") val packages: List<PackageDetail> = listOf(),
        @SerialName("data_usage_remaining_bytes") val dataUsageRemainingBytes: Long = 0,
        @SerialName("data_usage_remaining_gigabytes") val dataUsageRemainingGigabytes: Int = 0
    )
}

typealias AssignedEsim = EsimRequest.Response