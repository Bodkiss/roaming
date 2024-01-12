package com.knowroaming.esim.app.domain.network

import com.knowroaming.esim.app.domain.data.EsimRequest
import com.knowroaming.esim.app.domain.data.OrderRequest
import com.knowroaming.esim.app.domain.data.PaymentRequest
import com.knowroaming.esim.app.domain.model.OrderDetail
import com.knowroaming.esim.app.domain.model.OrderInfo
import com.knowroaming.esim.app.util.ApiResult
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Headers
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query

interface ESimApi {

    @POST("customer_esims_list/")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun getESimsList(@Body data: EsimRequest): List<EsimRequest.Response>

    @POST("payments/")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun getEsimPaymentIntent(@Body data: PaymentRequest): ApiResult<PaymentRequest.Response>

    @GET("orders/{order_uuid}/")
    suspend fun getOrderInfoBy(
        @Path("order_uuid") id: String, @Query("encode_qr_code") encodeQRCode: Boolean = true
    ): ApiResult<OrderInfo>

    @POST("order_lookup/")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun getOrderDetails(
        @Body data: OrderRequest
    ): ApiResult<OrderDetail>
}