package com.knowroaming.esim.app.util.converter

import com.knowroaming.esim.app.domain.data.CustomerRegister
import com.knowroaming.esim.app.domain.data.CustomerResetPassword
import com.knowroaming.esim.app.domain.data.CustomerSignIn
import com.knowroaming.esim.app.domain.data.EsimRequest
import com.knowroaming.esim.app.domain.data.PaymentRequest
import com.knowroaming.esim.app.domain.model.Country
import com.knowroaming.esim.app.domain.model.OrderDetail
import com.knowroaming.esim.app.domain.model.PackagePlan
import com.knowroaming.esim.app.util.ApiResult
import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.Converter
import de.jensklingenberg.ktorfit.internal.TypeData
import io.ktor.client.statement.HttpResponse
import kotlin.reflect.typeOf

class ResponseConverterFactory : Converter.Factory {
    override fun suspendResponseConverter(
        typeData: TypeData, ktorfit: Ktorfit
    ): Converter.SuspendResponseConverter<HttpResponse, *>? {
        return when (typeData.typeInfo.kotlinType) {
            typeOf<List<Country>>() -> BaseResponse.converterFactoryOf<Country>()
            typeOf<List<PackagePlan>>() -> BaseResponse.converterFactoryOf<PackagePlan>()
            typeOf<List<EsimRequest.Response>>() -> BaseResponse.converterFactoryOf<EsimRequest.Response>()
            typeOf<ApiResult<OrderDetail>>() -> ApiResult.converterFactoryOf<OrderDetail>()
            typeOf<ApiResult<CustomerSignIn.Response>>() -> ApiResult.converterFactoryOf<CustomerSignIn.Response>()
            typeOf<ApiResult<PaymentRequest.Response>>() -> ApiResult.converterFactoryOf<PaymentRequest.Response>()
            typeOf<ApiResult<CustomerRegister.Response>>() -> ApiResult.converterFactoryOf<CustomerRegister.Response>()
            typeOf<ApiResult<CustomerResetPassword.Response>>() -> ApiResult.converterFactoryOf<CustomerResetPassword.Response>()

            else -> null
        }
    }
}