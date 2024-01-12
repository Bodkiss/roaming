package com.knowroaming.esim.app.presentation.model

import com.knowroaming.esim.app.domain.data.toCustomer
import com.knowroaming.esim.app.domain.model.Customer
import com.knowroaming.esim.app.domain.repository.AuthProvider
import com.knowroaming.esim.app.domain.service.AuthState
import com.knowroaming.esim.app.util.Event
import com.knowroaming.esim.app.util.Response
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class AuthViewModel(
    private val provider: AuthProvider,
) : ViewModel() {
    private val event = MutableStateFlow<Event<Customer>>(Event.Completed)

    val auth: Flow<AuthState?>
        get() = provider.auth.map {
            return@map when (it) {
                is AuthState.Registered -> it
                is AuthState.Authenticated -> it
                is AuthState.Updated -> it
                else -> null
            }
        }

    val notification: Flow<Event.Notification<*>?>
        get() = event.map {
            it as? Event.Notification<*>
        }

    val error: Flow<Event.Failure?>
        get() = event.map {
            it as? Event.Failure
        }

    val loading: Flow<Boolean>
        get() = event.map {
            it is Event.Loading
        }

    fun verifyEmail(code: String) {
        viewModelScope.launch {}
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            provider.login(email, password).collect { response ->
                when (response) {
                    is Response.Loading -> event.update { Event.Loading }
                    is Response.Error -> {
                        event.update { Event.Failure(response.message) }
                        provider.update(AuthState.Unauthenticated)
                        delay(1000)
                        event.update { Event.Completed }
                    }

                    is Response.Success -> response.data.let { session ->
                        if (session.authenticated && session.customer != null) {
                            provider.update(AuthState.Authenticated(session.customer))
                        } else {
                            provider.update(AuthState.Unauthenticated)
                        }

                        event.update { Event.Completed }
                    }
                }
            }
        }
    }

    fun register(
        email: String,
        password: String,
        firstName: String = "",
        lastName: String = "",
        phoneNumber: String? = null
    ) {
        viewModelScope.launch {
            provider.register(email, password, firstName, lastName, phoneNumber)
                .collect { response ->
                    when (response) {
                        is Response.Error -> {
                            provider.update(AuthState.Unauthenticated)
                            event.update { Event.Failure(response.message) }
                            delay(1000)
                            event.update { Event.Completed }
                        }

                        is Response.Success -> response.data.let { registration ->
                            if (registration.success == true) {

                                val customer = registration.toCustomer(phoneNumber, firstName, lastName)

                                provider.update(
                                    AuthState.Registered(
                                        customer
                                    )
                                )

                                event.update {
                                    Event.Notification(registration.message, customer)
                                }
                            } else {
                                provider.update(AuthState.Unauthenticated)
                            }

                            event.update { Event.Completed }
                        }

                        is Response.Loading -> event.update { Event.Loading }
                    }
                }
        }
    }

    fun forgotPassword(email: String) {
        viewModelScope.launch {
            provider.forgetPassword(email).collect { response ->
                when (response) {
                    is Response.Loading -> event.update { Event.Loading }
                    is Response.Error -> event.update { Event.Failure(response.message) }
                    is Response.Success -> {}
                }
                event.update { Event.Completed }
            }
        }
    }

    fun logout() {
        provider.logout()
    }


    fun editDetails(
        email: String,
        password: String,
        firstName: String = "",
        lastName: String = "",
        phoneNumber: String? = null
    ) {
        viewModelScope.launch {
            provider.editDetails(email, password, firstName, lastName, phoneNumber)
                .collect { response ->
                    when (response) {
                        is Response.Error -> {
                            event.update { Event.Failure(response.message) }
                            delay(1000)
                            event.update { Event.Completed }
                        }

                        is Response.Success -> response.data.let { registration ->
                            if (registration.success == true) {
                                val customer = Customer(
                                    id = registration.id,
                                    email = registration.email,
                                    firstName = firstName,
                                    lastName = lastName,
                                    phoneNumber = phoneNumber,
                                    fullName = "$firstName $lastName",
                                )

                                provider.update(
                                    AuthState.Updated(
                                        customer
                                    )
                                )


                            }

                            event.update { Event.Completed }
                        }

                        is Response.Loading -> event.update { Event.Loading }
                    }
                }
        }
    }
}

