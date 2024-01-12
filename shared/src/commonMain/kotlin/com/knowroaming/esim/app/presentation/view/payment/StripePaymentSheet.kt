package com.knowroaming.esim.app.presentation.view.payment

import androidx.compose.runtime.Composable
import com.knowroaming.esim.app.domain.data.PaymentRequest


sealed interface PaymentResult {
    data class Failed(val error: String) : PaymentResult
    data object Canceled : PaymentResult
    data object Completed : PaymentResult
}

@Composable
expect fun StripePaymentSheet(
    enabled: Boolean,
    isLoading: Boolean,
    onRequest: ((PaymentRequest.Response) -> Unit) -> Unit,
    onResult: (result: PaymentResult, orderId: String?) -> Unit,
)
