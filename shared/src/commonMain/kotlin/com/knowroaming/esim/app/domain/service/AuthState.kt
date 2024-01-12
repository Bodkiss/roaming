package com.knowroaming.esim.app.domain.service

import com.knowroaming.esim.app.domain.model.Customer

sealed interface AuthState {
    data object Unauthenticated : AuthState
    data class Registered(var user: Customer) : AuthState
    data class Updated(var user: Customer) : AuthState
    data class Authenticated(var user: Customer) : AuthState
}