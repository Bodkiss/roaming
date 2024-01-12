package com.knowroaming.esim.app.presentation.view.payment

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.knowroaming.esim.app.domain.data.PaymentRequest
import com.knowroaming.esim.app.presentation.components.button.AppButton
import com.knowroaming.esim.app.presentation.components.button.AppButtonVariant
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.stripe.android.paymentsheet.rememberPaymentSheet

@Composable
actual fun StripePaymentSheet(
    enabled: Boolean,
    isLoading: Boolean,
    onRequest: ((PaymentRequest.Response) -> Unit) -> Unit,
    onResult: (result: PaymentResult, orderId: String?) -> Unit,
) {

    var orderId by remember { mutableStateOf<String?>(null) }

    fun onPaymentSheetResult(result: PaymentSheetResult) {
        when (result) {
            is PaymentSheetResult.Failed -> result.error.localizedMessage?.let {
                PaymentResult.Failed(
                    it
                )
            }?.let { onResult(it, orderId) }

            is PaymentSheetResult.Canceled -> onResult(PaymentResult.Canceled, orderId)
            is PaymentSheetResult.Completed -> onResult(PaymentResult.Completed, orderId)
        }
    }

    val context = LocalContext.current
    val sheet = rememberPaymentSheet(::onPaymentSheetResult)

    AppButton(
        enabled = enabled,
        loading = isLoading,
        onClick = {
            onRequest { payment ->
                if (!payment.isIntent) return@onRequest
                if (payment.publishableKey == null) return@onRequest

                PaymentConfiguration.init(
                    context, payment.publishableKey
                )


                if (payment.customerRef !== null && payment.ephemeralKey !== null) {
                    val configuration = PaymentSheet.CustomerConfiguration(
                        payment.customerRef, payment.ephemeralKey
                    )

                    openPaymentSheet(sheet, configuration, payment.uri)
                }

                orderId = payment.orderId
            }
        },
        text = "Proceed to Payment",
        type = AppButtonVariant.Primary,
    )
}

private fun openPaymentSheet(
    sheet: PaymentSheet, configuration: PaymentSheet.CustomerConfiguration, secret: String
) {
    sheet.presentWithPaymentIntent(
        secret, PaymentSheet.Configuration(
            customer = configuration,
            merchantDisplayName = "Know Roaming",
        )
    )
}