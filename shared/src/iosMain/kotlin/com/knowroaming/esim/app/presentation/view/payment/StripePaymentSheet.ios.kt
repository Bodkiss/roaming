package com.knowroaming.esim.app.presentation.view.payment

import androidx.compose.runtime.Composable
import com.knowroaming.esim.app.domain.data.PaymentRequest

@Composable
actual fun StripePaymentSheet(
    enabled: Boolean,
    isLoading: Boolean,
    onRequest: ((PaymentRequest.Response) -> Unit) -> Unit,
    onResult: (result: PaymentResult, orderId: String?) -> Unit,
) {
}