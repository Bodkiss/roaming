package com.knowroaming.esim.app.domain.network

import com.knowroaming.esim.app.domain.data.CustomerForgetPassword
import com.knowroaming.esim.app.domain.data.CustomerRegister
import com.knowroaming.esim.app.domain.data.CustomerResetPassword
import com.knowroaming.esim.app.domain.data.CustomerSignIn
import com.knowroaming.esim.app.util.ApiResult
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.Headers
import de.jensklingenberg.ktorfit.http.PATCH
import de.jensklingenberg.ktorfit.http.POST


interface AuthAPI {

    @POST("customer_register/")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun register(@Body data: CustomerRegister): ApiResult<CustomerRegister.Response>

    @POST("customer_sign_in/")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun login(@Body data: CustomerSignIn): ApiResult<CustomerSignIn.Response>

    @POST("customer_reset_password/")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun resetPassword(@Body data: CustomerResetPassword): ApiResult<CustomerResetPassword.Response>

    @POST("customer_forgot_password/")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun forgetPassword(@Body data: CustomerForgetPassword)

    @PATCH("customer_edit_details/")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun editeDetails(@Body data: CustomerRegister): ApiResult<CustomerRegister.Response>
}