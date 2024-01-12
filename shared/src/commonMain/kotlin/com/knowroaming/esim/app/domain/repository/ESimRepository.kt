package com.knowroaming.esim.app.domain.repository

import com.knowroaming.esim.app.domain.data.EsimRequest
import com.knowroaming.esim.app.domain.data.PaymentRequest
import com.knowroaming.esim.app.domain.model.OrderDetail
import com.knowroaming.esim.app.domain.model.OrderInfo
import com.knowroaming.esim.app.util.Response
import kotlinx.coroutines.flow.Flow

interface ESimRepository {
    suspend fun getCustomerEsimListById(id: String): Flow<Response<List<EsimRequest.Response>>>

    suspend fun getEsimPaymentIntent(request: PaymentRequest): Flow<Response<PaymentRequest.Response>>

    suspend fun getOrderInfoBy(id: String, encodeQrCode: Boolean = true): Flow<Response<OrderInfo>>

    suspend fun getOrderDetailBy(
        id: String, encodeQrCode: Boolean = true
    ): Flow<Response<OrderDetail>>
}