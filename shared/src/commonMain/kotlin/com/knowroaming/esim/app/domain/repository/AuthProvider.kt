package com.knowroaming.esim.app.domain.repository

import com.knowroaming.esim.app.domain.data.CustomerRegister
import com.knowroaming.esim.app.domain.data.CustomerSignIn
import com.knowroaming.esim.app.domain.service.AuthState
import com.knowroaming.esim.app.util.Response
import kotlinx.coroutines.flow.Flow

interface AuthProvider {

    val auth: Flow<AuthState>

    fun update(state: AuthState)

    fun login(email: String, password: String): Flow<Response<CustomerSignIn.Response>>

    fun logout()

    fun register(
        email: String,
        password: String,
        firstName: String = "",
        lastName: String = "",
        phoneNumber: String? = null
    ): Flow<Response<CustomerRegister.Response>>

    fun resetPassword(id: String, confirmation: String): Flow<Response<Boolean>>

    fun resetPassword(id: String, password: String, confirmation: String): Flow<Response<Boolean>>

    fun forgetPassword(email: String): Flow<Response<Boolean>>

    fun editDetails(
        email: String,
        password: String,
        firstName: String = "",
        lastName: String = "",
        phoneNumber: String? = null
    ): Flow<Response<CustomerRegister.Response>>
}