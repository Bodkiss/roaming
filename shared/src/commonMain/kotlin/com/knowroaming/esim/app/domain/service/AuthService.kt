package com.knowroaming.esim.app.domain.service

import com.knowroaming.esim.app.domain.data.AuthData
import com.knowroaming.esim.app.domain.data.CustomerRegister
import com.knowroaming.esim.app.domain.data.CustomerSignIn
import com.knowroaming.esim.app.domain.data.toCustomer
import com.knowroaming.esim.app.domain.model.Customer
import com.knowroaming.esim.app.domain.network.AuthAPI
import com.knowroaming.esim.app.domain.repository.AuthProvider
import com.knowroaming.esim.app.util.ApiResult
import com.knowroaming.esim.app.util.Response
import com.knowroaming.esim.app.util.SharedStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json


class AuthService(val api: AuthAPI) : AuthProvider {

    private val authDateKey = "auth_data"

    private val stateFlow = MutableStateFlow<AuthState>(AuthState.Unauthenticated)


    init {
        try {
            val json = SharedStorage.secureLoad(authDateKey, "")

            val response = Json.decodeFromString(AuthData.serializer(), json)

            if (response.authenticated && response.customer !== null) {
                stateFlow.update { AuthState.Authenticated(response.customer) }
            } else if (response.customer !== null) {
                stateFlow.update { AuthState.Registered(response.customer) }
            } else {
                stateFlow.update { AuthState.Unauthenticated }
            }
        } catch (e: SerializationException) {
            stateFlow.update { AuthState.Unauthenticated }
        } catch (_: Exception) {
        }
    }

    private fun save(data: AuthData) {
        SharedStorage.secureSave(Json.encodeToString(AuthData.serializer(), data), authDateKey)
    }

    private fun clear() {
        SharedStorage.clearSecureStorage()
        update(AuthState.Unauthenticated)
    }

    override val auth: Flow<AuthState>
        get() = stateFlow

    override fun update(state: AuthState) {
        stateFlow.update { state }
    }

    override fun login(email: String, password: String): Flow<Response<CustomerSignIn.Response>> {
        return flow {
            try {
                emit(Response.Loading)

                val response = api.login(
                    CustomerSignIn(
                        email = email, password = password
                    )
                )

                when (response) {
                    is ApiResult.Error -> emit(Response.Error(response.detail))
                    is ApiResult.Success -> {
                        save(response.data)
                        emit(Response.Success(response.data))
                    }
                }
            } catch (e: Exception) {
                Response.Error(e.message)
            }
        }
    }

    override fun logout() {
        clear()
    }

    override fun register(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phoneNumber: String?,
    ): Flow<Response<CustomerRegister.Response>> {
        return flow {
            try {
                emit(Response.Loading)

                val response = api.register(
                    CustomerRegister(
                        email = email,
                        firstName = firstName,
                        lastName = lastName,
                        fullName = "$firstName $lastName",
                        phoneNumber = phoneNumber,
                        password = password
                    )
                )

                when (response) {
                    is ApiResult.Error -> emit(Response.Error(response.detail))
                    is ApiResult.Success -> {
                        save(
                            AuthData(
                                customer = Customer(
                                    id = response.data.id,
                                    email = response.data.email,
                                    firstName = firstName,
                                    lastName = lastName,
                                    fullName = "$firstName $lastName",
                                    phoneNumber = phoneNumber,
                                )
                            )
                        )

                        emit(
                            Response.Success(
                                response.data
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                emit(Response.Error(e.message))
            }
        }
    }

    override fun resetPassword(id: String, confirmation: String): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun resetPassword(
        id: String, password: String, confirmation: String
    ): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun forgetPassword(email: String): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun editDetails(email: String, password: String, firstName: String, lastName: String, phoneNumber: String?): Flow<Response<CustomerRegister.Response>> {
        return flow {
            try {
                emit(Response.Loading)
                val json = SharedStorage.secureLoad(authDateKey, "")

                val customerResponse = Json.decodeFromString(AuthData.serializer(), json)

                val response = api.editeDetails(

                    CustomerRegister(
                        id = customerResponse.customer?.id,
                        email = email,
                        firstName = firstName,
                        lastName = lastName,
                        fullName = "$firstName $lastName",
                        phoneNumber = phoneNumber,
                        password = password
                    )
                )
                when (response) {
                    is ApiResult.Error -> emit(Response.Error(response.detail))
                    is ApiResult.Success -> {

                        stateFlow.update {
                            AuthState.Updated(
                                response.data.toCustomer(phoneNumber, firstName, lastName)
                            )
                        }

                        save(
                            AuthData(
                                customer = Customer(
                                    id = response.data.id,
                                    email = response.data.email,
                                    firstName = firstName,
                                    lastName = lastName,
                                    fullName = "$firstName $lastName",
                                    phoneNumber = phoneNumber,
                                )
                            )
                        )

                        emit(
                            Response.Success(
                                response.data
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                emit(Response.Error(e.message))
            }
        }
    }

}