package com.knowroaming.esim.app.domain.service

import com.knowroaming.esim.app.domain.data.EsimRequest
import com.knowroaming.esim.app.domain.data.OrderRequest
import com.knowroaming.esim.app.domain.data.PaymentRequest
import com.knowroaming.esim.app.domain.model.OrderDetail
import com.knowroaming.esim.app.domain.model.OrderInfo
import com.knowroaming.esim.app.domain.network.ESimApi
import com.knowroaming.esim.app.domain.repository.ESimRepository
import com.knowroaming.esim.app.util.ApiResult
import com.knowroaming.esim.app.util.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ESimService(val api: ESimApi) : ESimRepository {
    override suspend fun getCustomerEsimListById(id: String): Flow<Response<List<EsimRequest.Response>>> {
        return flow {
            try {
                emit(Response.Loading)
                emit(
                    Response.Success(
                        api.getESimsList(
                            EsimRequest(id, getPackageDetails = true, getBalanceRemaining = true)
                        )
                    )
                )
            } catch (e: Exception) {
                emit(Response.Error(e.message))
            }
        }
    }


    override suspend fun getEsimPaymentIntent(request: PaymentRequest): Flow<Response<PaymentRequest.Response>> {
        return flow {
            try {
                emit(Response.Loading)

                when (val response = api.getEsimPaymentIntent(request)) {
                    is ApiResult.Error -> emit(Response.Error(response.detail))
                    is ApiResult.Success -> emit(Response.Success(response.data))
                }
            } catch (e: Exception) {
                emit(Response.Error(e.message))
            }
        }
    }

    override suspend fun getOrderInfoBy(
        id: String, encodeQrCode: Boolean
    ): Flow<Response<OrderInfo>> {
        return flow {
            try {
                emit(Response.Loading)

                when (val response = api.getOrderInfoBy(id, encodeQrCode)) {
                    is ApiResult.Error -> emit(Response.Error(response.detail))
                    is ApiResult.Success -> emit(Response.Success(response.data))
                }
            } catch (e: Exception) {
                emit(Response.Error(e.message))
            }
        }
    }

    override suspend fun getOrderDetailBy(
        id: String, encodeQrCode: Boolean
    ): Flow<Response<OrderDetail>> {
        return flow {
            try {
                emit(Response.Loading)

                when (val response = api.getOrderDetails(OrderRequest(id, encodeQrCode))) {
                    is ApiResult.Error -> emit(Response.Error(response.detail))
                    is ApiResult.Success -> emit(Response.Success(response.data))
                }
            } catch (e: Exception) {
                emit(Response.Error(e.message))
            }
        }
    }
}